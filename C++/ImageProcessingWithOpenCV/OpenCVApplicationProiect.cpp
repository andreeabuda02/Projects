#include "stdafx.h"
#include <opencv2/core/utils/logger.hpp>
#include <opencv2/highgui.hpp>
#include <opencv2/imgproc.hpp>
#include <opencv2/opencv.hpp>
#include <vector>  
#include <queue> 
#include <random>
#include "common.h"

wchar_t* projectPath;

using namespace cv;
using namespace std;

/* Această funcție redimensionează imaginile pentru a le uniformiza dimensiunile.
   Aceasta este utilă pentru a asigura o analiză consistentă și corectă a imaginilor,
   evitând discrepanțele în dimensiunile acestora.
*/

Mat redimensioneazaImagine(Mat img, int newWidth, int newHeight)
{
	Mat resizedImg(newHeight, newWidth, img.type());

	for (int y = 0; y < newHeight; y++) {
		for (int x = 0; x < newWidth; x++) {
			int originalX = static_cast<int>(x * static_cast<float>(img.cols) / newWidth);
			int originalY = static_cast<int>(y * static_cast<float>(img.rows) / newHeight);
			resizedImg.at<Vec3b>(y, x) = img.at<Vec3b>(originalY, originalX);
		}
	}

	return resizedImg;
}


/* Funcția corectează culorile imaginilor pentru a asigura o reprezentare precisă, mai ales în
   ceea ce privește culorile diferite ale lichidelor. Acest lucru este important pentru o analiză
   corectă a culorilor și a compoziției.
*/

Mat corecteazaCuloriRGB2GRAY(Mat src)
{
	int inaltime = src.rows;
	int latime = src.cols;
	Mat imagineAjustata = Mat(inaltime, latime, CV_8UC1);

	for (int i = 0; i < inaltime; i++)
	{
		for (int j = 0; j < latime; j++)
		{
			Vec3b v3 = src.at<Vec3b>(i, j);
			uchar b = v3[0];
			uchar g = v3[1];
			uchar r = v3[2];
			imagineAjustata.at<uchar>(i, j) = (r + g + b) / 3;
		}
	}

	return imagineAjustata;
}


/* Funcția calculează deviația standard locală a regiunilor imaginii, contribuind la
   îmbunătățirea contrastului și la reducerea zgomotului. Acest lucru este important pentru o
   analiză mai precisă și pentru a evidenția detaliile relevante din imagine.
*/


float calculeazaDevStdLocala(Mat img) {
	float deviatia = 0, media = 0, pixeli = 0, pixeli2 = 0;
	for (int i = 1; i < img.rows - 1; i++) {
		for (int j = 1; j < img.cols - 1; j++) {
			pixeli += img.at<uchar>(i, j);
		}
	}
	media = pixeli / (img.rows * img.cols);
	for (int i = 1; i < img.rows - 1; i++) {
		for (int j = 1; j < img.cols - 1; j++) {
			pixeli2 += pow(img.at<uchar>(i, j) - media, 2);
		}
	}
	media = pixeli2 / (img.rows * img.cols);
	deviatia = sqrt(media);
	printf("%f\n", deviatia);

	return deviatia;

}

/* Această funcție aplică metoda deviației standard locale (LSD) pentru a îmbunătăți
   contrastul și a reduce zgomotul în regiuni specifice ale imaginii. Acest lucru contribuie
   la obținerea unei imagini mai clare și mai ușor de analizat.
*/
// Funcția pentru calcularea mediei și deviației standard locale
void calculeazaMedieDevLocala(const Mat& img, Mat& mediaLocala, Mat& devLocala, int kernelSize) {
	Mat imgFloat;
	img.convertTo(imgFloat, CV_32F);
	blur(imgFloat, mediaLocala, Size(kernelSize, kernelSize));
	Mat imgSquared;
	pow(imgFloat, 2, imgSquared);
	Mat meanOfSquares;
	blur(imgSquared, meanOfSquares, Size(kernelSize, kernelSize));
	Mat temp;
	pow(mediaLocala, 2, temp);
	sqrt(meanOfSquares - temp, devLocala);
}

// Funcția pentru aplicarea segmentării LSD folosind deviația standard locală
Mat aplicaLSD(const Mat& img) {
	int kernelSize = 3; // Dimensiunea nucleului pentru medie și deviație standard
	float D = 1.0; // Constanta pentru contrast
	Mat imgGray;
	if (img.channels() == 3) {
		cvtColor(img, imgGray, COLOR_BGR2GRAY);
	}
	else {
		imgGray = img.clone();
	}

	Mat mediaLocala, devLocala;
	calculeazaMedieDevLocala(imgGray, mediaLocala, devLocala, kernelSize);

	Mat imgFloat;
	imgGray.convertTo(imgFloat, CV_32F);

	Mat imgLSD = mediaLocala + D * ((imgFloat - mediaLocala) / (devLocala + 1e-10));

	Mat imgLSD_8U;
	imgLSD.convertTo(imgLSD_8U, CV_8U);
	return imgLSD_8U;
}


/* Această funcție aplică algoritmul Canny pentru detectarea marginilor în imagine.
   Algoritmul Canny este utilizat pentru a identifica contururile obiectelor din imagine,
   ceea ce este esențial pentru analiza formei și conturului.
*/

Mat AlgoritmulCanny(Mat src)
{
	Mat dst, gauss;
	double k = 0.4;
	int pH = 50;
	int pL = (int)k * pH;
	GaussianBlur(src, gauss, Size(5, 5), 0.8, 0.8);
	Canny(gauss, dst, pL, pH, 3);
	return dst;
}



/* Această funcție aplică Transformata Hough pentru detectarea liniilor din imagine.
   Acest lucru poate fi util pentru detectarea formei sticlei sau a nivelului lichidului, contribuind la analiza și interpretarea datelor.
*/

Mat aplicaTransformataHough(Mat img)
{
	Mat imgEdges;
	Canny(img, imgEdges, 50, 150, 3);
	vector<Vec4i> lines;
	HoughLinesP(imgEdges, lines, 1, CV_PI / 180, 50, 30, 10);

	Mat imgHough = img.clone();
	for (size_t i = 0; i < lines.size(); i++) {
		Vec4i l = lines[i];
		line(imgHough, Point(l[0], l[1]), Point(l[2], l[3]), Scalar(0, 0, 255), 3, LINE_AA);
	}

	return imgEdges;
}

/* Această funcție aplică binarizarea adaptivă pentru a segmenta imaginea în două clase:
   pixeli de fundal și pixeli de obiect. Acest lucru este important pentru separarea obiectului de fundal.
*/

Mat binarizareAdaptiva(Mat img) {

	Mat dst = img.clone();

	int height = img.rows;
	int width = img.cols;
	int M = height * width;
	int minInIndex = 255, maxInIndex = 0;
	float T1, T2 = 0.0;

	float G1 = 0.0, G2 = 0.0, error = 0.1;

	int h[256] = { 0 };
	uchar pixel;

	for (int i = 0; i < height; i++) {
		for (int j = 0; j < width; j++) {
			pixel = img.at<uchar>(i, j);
			h[pixel]++;
		}
	}

	for (int i = 0; i < 256; i++) {
		if (h[i]) {
			if (i < minInIndex) {
				minInIndex = i;
			}
			if (i > maxInIndex) {
				maxInIndex = i;
			}
		}
	}

	T1 = (maxInIndex + minInIndex) / 2.0f;

	while (abs(T1 - T2) > error) {
		int N1 = 0, N2 = 0;

		for (int i = minInIndex; i <= T1; i++) {
			G1 += i * h[i];
			N1 += h[i];
		}
		for (int i = T1 + 1; i < maxInIndex; i++) {
			G2 += i * h[i];
			N2 += h[i];
		}

		G1 /= N1;
		G2 /= N2;

		T2 = T1;
		T1 = (G1 + G2) / 2.0f;
	}

	for (int i = 0; i < height; i++) {
		for (int j = 0; j < width; j++) {
			if (img.at<uchar>(i, j) < T1) {
				dst.at<uchar>(i, j) = 0;
			}
			else {
				dst.at<uchar>(i, j) = 255;
			}
		}
	}

	return dst;
}

//////////////////////////////////

// Funcții pentru detectarea și desenarea contururilor


Mat ero8(Mat img) {
	int height = img.rows;
	int width = img.cols;

	Mat dst = img.clone();

	for (int i = 1; i < height - 1; i++) {
		for (int j = 1; j < width - 1; j++) {
			if (img.at<uchar>(i, j) == 0) {
				for (int k = 0; k < 3; k++) {
					for (int q = 0; q < 3; q++) {
						if (img.at<uchar>((i + k - 1), (j + q - 1)) == 255) {
							dst.at<uchar>(i, j) = 255;
						}
					}
				}
			}
		}
	}

	return dst;
}

Mat extrContur(Mat img1, Mat img2) {
	int height = img1.rows;
	int width = img1.cols;

	Mat dst = img2.clone();

	for (int i = 1; i < height; i++) {
		for (int j = 1; j < width; j++) {
			if (img1.at<uchar>(i, j) != img2.at<uchar>(i, j)) {
				dst.at<uchar>(i, j) = img1.at<uchar>(i, j);
			}
			else {
				dst.at<uchar>(i, j) = 255;
			}
		}
	}

	return dst;
}


Mat detecteazaContururi(Mat img) {
	Mat dst = ero8(img);

	Mat img2 = dst.clone();

	dst = extrContur(img, img2);
	 
	return dst;

}


//////////////////////////////////////
//Deschidere

Mat open(Mat src)
{
	int height = src.rows;
	int width = src.cols;
	int i = 0;
	int j = 0;
	int auxx = 0;
	int auxy = 0;
	int x = 0;
	int y = 0;

	Mat structural(3, 3, CV_8UC1, Scalar(255));
	Mat dst(height, width, CV_8UC1, Scalar(255));

	structural.at<uchar>(0, 1) = 0;
	structural.at<uchar>(1, 1) = 0;
	structural.at<uchar>(1, 0) = 0;
	structural.at<uchar>(1, 2) = 0;
	structural.at<uchar>(2, 1) = 0;

	// Eroziune
	Mat temp = src.clone();

	for (i = 1; i < height - 1; i++)
	{
		for (j = 1; j < width - 1; j++)
		{
			bool erodePixel = true;
			for (x = 0; x < 3; x++)
			{
				for (y = 0; y < 3; y++)
				{
					if (structural.at<uchar>(x, y) == 0) {
						auxx = i + x - (structural.rows / 2);
						auxy = j + y - (structural.cols / 2);
						if (temp.at<uchar>(auxx, auxy) == 255) {
							erodePixel = false;
							break;
						}
					}
				}
				if (!erodePixel)
				{
					break;
				}
			}
			if (erodePixel)
			{
				temp.at<uchar>(i, j) = 0;
			}
		}
	}

	// Dilatare
	dst = temp.clone();

	for (i = 1; i < height - 1; i++)
	{
		for (j = 1; j < width - 1; j++)
		{
			if (src.at<uchar>(i, j) == 0)
			{
				for (x = 0; x < 3; x++)
				{
					for (y = 0; y < 3; y++)
					{
						if (structural.at<uchar>(x, y) == 0) {
							auxx = i + x - (structural.rows / 2);
							auxy = j + y - (structural.cols / 2);
							if (auxx >= 0 && auxx < height && auxy >= 0 && auxy < width) {
								dst.at<uchar>(auxx, auxy) = 0;
							}
						}
					}
				}
			}
		}
	}


	return dst;
}


/////////////////////

Mat liniiO(Mat img) {
	Mat orizontal = img.clone();
	int size = orizontal.cols / 30;
	
	Mat os = getStructuringElement(MORPH_RECT, Size(size, 1));
	erode(orizontal, orizontal, os, Point(-1, -1));
	dilate(orizontal, orizontal, os, Point(-1, -1));

	return orizontal;

}

///

void afisare(Mat img1, Mat img2) {
	int height = img1.rows;
	int width = img1.cols;
	int gasit = 0;
	int x = height - 1;

	for (int i = height - 1; i >= 0; i--)
	{
		for (int j = 0; j < width; j++)
		{
			if (img1.at<uchar>(i, j) == 255 && x - i > 5)
			{
				gasit++;
				x = i;
				break;
			}
			
		}
		if (gasit == 2)
			break;
	}
	line(img2, Point(0, x), Point(width-1, x), Scalar(0, 0, 255), 5, LINE_AA);
	imshow("Nivelul lichidului", img2);
}



int main()
{
	cv::utils::logging::setLogLevel(cv::utils::logging::LOG_LEVEL_FATAL);
	projectPath = _wgetcwd(0, 0);

	Mat img;
	img = imread("ex.jpg");

	int op;
	char fname[MAX_PATH];

	do {
		system("cls");
		destroyAllWindows();
		printf("Menu:\n");
		printf(" 1 - Redimensioneaza imagine\n");
		printf(" 2 - Corecteaza culori în Grayscale\n");
		printf(" 3 - Calculeaza deviatia standard locala\n");
		printf(" 4 - Aplica LSD\n");
		printf(" 5 - Algoritmul Canny\n");
		printf(" 6 - Aplica Transformata Hough\n");
		printf(" 7 - Binarizare Adaptiva\n");
		printf(" 8 - Detecteaza Contururile\n");
		printf("9 - Open\n");
		printf("10 - Detectare linii orizontale\n");
		printf("11 - Afisale lnii de nivel lichid1\n");
		printf("12 - Afisale lnii de nivel lichid2\n");

		printf(" 0 - Exit\n\n");
		printf("Option: ");
		scanf("%d", &op);

		switch (op)
		{
		case 1: {

			while (openFileDlg(fname)) {
				Mat img = imread(fname);
				if (img.empty()) {
					printf("Imagine invalida!\n");
					continue;
				}

				Mat i = redimensioneazaImagine(img, 500, 500);
				imshow("Input image", img);
				imshow("Resized image", i);
				waitKey();
			}

			break;
		}
		case 2: {
			while (openFileDlg(fname)) {
				Mat img = imread(fname);
				if (img.empty()) {
					printf("Imagine invalida!\n");
					continue;
				}
				Mat imagineAjustata = corecteazaCuloriRGB2GRAY(img);
				imshow("Input image", img);
				imshow("Imagine ajustata", imagineAjustata);

				waitKey();
			}
			break;
		}
		case 3: {

			while (openFileDlg(fname)) {
				Mat img = imread(fname);
				if (img.empty()) {
					printf("Imagine invalida!\n");
					continue;
				}
				img = corecteazaCuloriRGB2GRAY(img);
				calculeazaDevStdLocala(img);
				waitKey();
			}

			break;


		}
		case 4: {
			while (openFileDlg(fname)) {
				Mat img = imread(fname);
				if (img.empty()) {
					printf("Imagine invalida!\n");
					continue;
				}
				Mat imagine = aplicaLSD(img);
				imshow("Input image", img);
				imshow("final", imagine);
				waitKey();
			}

			break;
		}
		case 5: {

			while (openFileDlg(fname)) {
				Mat img = imread(fname);
				if (img.empty()) {
					printf("Imagine invalida!\n");
					continue;
				}

				Mat imagine = AlgoritmulCanny(img);
				imshow("Input image", img);
				imshow("canny", imagine);

				waitKey();
			}


			break;
		}
		case 6:
			while (openFileDlg(fname)) {
				Mat img = imread(fname, IMREAD_GRAYSCALE);
				if (img.empty()) {
					printf("Imagine invalida!\n");
					continue;
				}

				Mat imgEdges = aplicaTransformataHough(img);
				imshow("Input image", img);
				imshow("Imaginea cu Transformata Hough", imgEdges);

				waitKey();
			}

			break;
		case 7: {
			while (openFileDlg(fname)) {
				Mat img = imread(fname);
				if (img.empty()) {
					printf("Imagine invalida!\n");
					continue;
				}

				img = corecteazaCuloriRGB2GRAY(img);
				Mat imagine = binarizareAdaptiva(img);
				imshow("Input image", img);
				imshow("Binarizata", imagine);

				waitKey();
			}
			break;
		}
		case 8: {
			while (openFileDlg(fname)) {
				Mat img = imread(fname);
				if (img.empty()) {
					printf("Imagine invalida!\n");
					continue;
				}

				img = corecteazaCuloriRGB2GRAY(img);
				Mat imagine = binarizareAdaptiva(img);
				Mat imagine1 = detecteazaContururi(imagine);
				imshow("Contur", imagine1);

				waitKey();
			}
			break;
		}

		case 9: {
			while (openFileDlg(fname)) {
				Mat img = imread(fname);
				if (img.empty()) {
					printf("Imagine invalida!\n");
					continue;
				}

				img = corecteazaCuloriRGB2GRAY(img);
				Mat imagine = binarizareAdaptiva(img);
				Mat imagine1, imagine2 = imagine.clone();
				for (int i = 0; i < 1; i++) {
					imagine1 = open(imagine2);
					imagine2 = imagine1.clone();
				}
				imshow("Open", imagine2);

				waitKey();
			}
			break;
		}

		case 10: {
			while (openFileDlg(fname)) {
				Mat img = imread(fname);
				if (img.empty()) {
					printf("Imagine invalida!\n");
					continue;
				}

				Mat imgG = corecteazaCuloriRGB2GRAY(img);
				Mat imagine = binarizareAdaptiva(imgG);
				Mat imagine3;
				bitwise_not(imagine, imagine3);
				Mat imagine1, imagine2 = imagine3.clone();
				
				for (int i = 0; i < 5; i++) {
					imagine1 = open(imagine2);
					imagine2 = imagine1.clone();
				}

				Mat ds = liniiO(aplicaTransformataHough(imagine2));
				
				imshow("linii orizontale", ds);
				afisare(ds,img);

				waitKey();
			}
			break;
		}

		case 11: {
			while (openFileDlg(fname)) {
				Mat img = imread(fname);
				if (img.empty()) {
					printf("Imagine invalida!\n");
					continue;
				}
				Mat imgG = corecteazaCuloriRGB2GRAY(img);
				Mat imagine = binarizareAdaptiva(imgG);
				Mat imagine3;
				bitwise_not(imagine, imagine3);
				Mat imagine1, imagine2 = imagine3.clone();
				
				for (int i = 0; i < 5; i++) {
					imagine1 = open(imagine2);
					imagine2 = imagine1.clone();
				}

				Mat ds = liniiO(aplicaTransformataHough(imagine2));
				
				imshow("linii orizontale", ds);
				afisare(ds,img);
				waitKey();
			}
			break;
		}


		case 12: {
			while (openFileDlg(fname)) {
				Mat img = imread(fname);
				if (img.empty()) {
					printf("Imagine invalida!\n");
					continue;
				}

				Mat i = redimensioneazaImagine(img, 500, 500);
				Mat imgG = corecteazaCuloriRGB2GRAY(i);
				Mat imgZ = aplicaLSD(imgG);
				Mat imagine = binarizareAdaptiva(imgZ);
				Mat imagine3;
				bitwise_not(imagine, imagine3);
				Mat imagine1, imagine2 = imagine3.clone();

				for (int i = 0; i < 5; i++) {
					imagine1 = open(imagine2);
					imagine2 = imagine1.clone();
				}

				Mat ds = liniiO(aplicaTransformataHough(imagine2));

				imshow("linii", ds);

				afisare(ds, i);


				waitKey();
			}
			break;
		}
		default:
			break;
		}
	} while (op != 0);
	return 0;
}
