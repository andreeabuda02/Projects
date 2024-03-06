#include <stdio.h>  //informatii intrare/iesire
#include <stdlib.h>
#include <sys/types.h>  //
#include <sys/stat.h>   //pentru informatii despre fisiere
#include <unistd.h>   //pentru functii de sistem
#include <string.h>  //functii pentru operatii pe siruri de caractere
#include <dirent.h>  //pentru lucru cu directoare
#include <fcntl.h>  //pentru fisiere

/**
Structura
*/

typedef struct{
    char name[21];
    int type;
    int offset;
    int size;
}Header;

/**
- Functia primeste calea directorului; o variabila afisat care va decide daca se
va afisa mesajul descris de stare, "ERROR" sau "SUCCESS"; o variabila size_greater
care ajuta la afisarea fisierelor care au o dimensiune mai mare decat valoarea stocata
in variabila; un sir de caractere name_starts_with care ajuta la afisarea fisierelor
care au acelasi prefix cu sirul de caractere stocat in name_starts_with.

- Prima data se deschide directorul si verifica daca acesta este valid, iar daca acesta
nu este valid se va afisa mesajul "invalid directory path", iar in caz contrar, daca acesta
este valid si daca nu afost afisata inca starea de succes, adica variabila afisat inca are
valoarea egala cu 0, atunci se afiseaza " SUCCESS".
- Pe urma se citesc fisierele din directorul curent, excluzand directorul curent "." si
directorul parinte ".." deoarece acestea sunt intotdeauna prezente in director, iar pentru
fiecare fisier se creeaza o cale "fullPath" prin concatenarea caii directorului si a numelui
fisierelui.
- Cu functia lstat extragem informatii despre fisier. Verificam tipul fisierului prin conditiile
S_ISDIR(statbuf.st_mode) si S_ISREG(statbuf.st_mode), astfel determinam daca fisierul e director
sau registru.
- Daca fisierul este director verific daca numele lui incepe cu name_starts_with si
se apeleaza recursiv functia pentru a afisa fosierele si directoarele din el.
- Daca fisierul este registru se verifica dimensiunea si prefixul acestuia si se afiseaza sau nu,
in functie de a acestea.

*/

void afisareRecursiva(const char* dirPath, int afisat, int size_greater, char *name_starts_with)
{
    DIR *dir = NULL;
    struct dirent *entry = NULL;
    char fullPath[512];
    struct stat statbuf;

    dir = opendir(dirPath);
    if(dir == NULL)
    {
        printf("ERROR\ninvalid directory path\n");
        return;
    }
    else
        if(afisat==0)
        {
            printf("SUCCESS\n");
            afisat=1;
        }

    while((entry = readdir(dir)) != NULL)
    {
        if(strcmp(entry->d_name, ".") != 0 && strcmp(entry->d_name, "..") != 0)
        {
            snprintf(fullPath, 512, "%s/%s", dirPath, entry->d_name);
            if(lstat(fullPath, &statbuf) == 0)
            {
                if(name_starts_with[0]=='\0'&&size_greater==0)
                    printf("%s\n",fullPath);

                if(S_ISDIR(statbuf.st_mode))
                {
                    //if sa verific daca numele incepe cu ..
                    if(name_starts_with[0]!='\0'&&strncmp(entry->d_name,name_starts_with,strlen(name_starts_with))==0)
                    {
                        printf("%s\n",fullPath);
                    }
                    afisareRecursiva(fullPath,afisat,size_greater, name_starts_with);
                }

                if(S_ISREG(statbuf.st_mode))
                {
                    //if sa verific dimensiunea
                    // if sa verific daca numele incepe cu ..
                    if(name_starts_with[0]!='\0'&&strncmp(entry->d_name,name_starts_with,strlen(name_starts_with))==0)
                    {
                            printf("%s\n",fullPath);
                    }

                    if(statbuf.st_size > size_greater&&size_greater!=0)
                    {
                        printf("%s\n",fullPath);
                    }
                }
            }
        }
    }
    closedir(dir);
}

/**

- Aceasta functie este asemanatoare cu functia afisareRecursiva, diferenta este ca nu se apeleaza
recursiv si se afiseaza doar fisierele din directorul curent.
- Functia de asemenea primeste calea directorului; o variabila afisat care va decide daca se
va afisa mesajul descris de stare, "ERROR" sau "SUCCESS"; o variabila size_greater
care ajuta la afisarea fisierelor care au o dimensiune mai mare decat valoarea stocata
in variabila; un sir de caractere name_starts_with care ajuta la afisarea fisierelor
care au acelasi prefix cu sirul de caractere stocat in name_starts_with.
- Pentru inceput se deschide directorul si se verifica daca este valid si in functie de raspunsul
aceste verificari se va afisa un mesaj de eroare sau mesajul "SUCCESS".
- Pe urma se citesc fisierele excluzand directorul curent "." si directorul parinte "..".
- Pentru fiecare fisier citit se verifica daca numele lui incepe cu prefixul specificat prin
name_starts_with. Daca este adevarat se obtin informatii despre fisier si se verifica dimensiunea
lui cu size_greater.
- Daca fisierul verifica criteriteriul acesta este afisat.

*/

void afisareSimpla(const char* dirPath,int size_greater,char *name_starts_with)
{
    DIR *dir = NULL;
    struct dirent *entry = NULL;
    char fullPath[512];
    struct stat statbuf;

    dir = opendir(dirPath);
    if(dir == NULL)
    {
        printf("ERROR\ninvalid directory path\n");
        return;
    }
    else
        printf("SUCCESS\n");

    while((entry = readdir(dir)) != NULL)
    {
        if(strcmp(entry->d_name, ".") != 0 && strcmp(entry->d_name, "..") != 0)
        {
            snprintf(fullPath, 512, "%s/%s", dirPath, entry->d_name);
            if(lstat(fullPath, &statbuf) == 0)
            {

                if(name_starts_with[0]=='\0'&&size_greater==0)
                    printf("%s\n",fullPath);

                if(S_ISDIR(statbuf.st_mode))
                {
                    if(name_starts_with[0]!='\0'&&strncmp(entry->d_name,name_starts_with,strlen(name_starts_with))==0)
                    {
                        printf("%s\n",fullPath);
                    }
                }

                if(S_ISREG(statbuf.st_mode))
                {
                    //fac un if sa verific dimensiunea size gr
                    // fac un if sa verific daca numele incepe cu
                    if(name_starts_with[0]!='\0'&&strncmp(entry->d_name,name_starts_with,strlen(name_starts_with))==0)
                    {
                        printf("%s\n",fullPath);
                    }

                    if(statbuf.st_size > size_greater&&size_greater!=0)
                    {
                        printf("%s\n",fullPath);
                    }
                }
            }
        }
    }
    closedir(dir);
}

/**

- Functia primeste ca argumente calea catre fisierul care trebuie citit, o structura Header care
va fi completata cu informatii din fisier, o variabila nr_sectiuni care reprezinta numarul de
sectiuni din fisier, si un pointer la intreg, versiune, care va fi actualizat cu versiunea fisierului.
- Declar variabilele magic si header_size, un intreg pe 2 octeti, si fd, un descriptor de fisier.
- Deschid fisierul pentru citire cu open. Citesc primul caracter din fisier in variabila magic
si verific daca acesta este egala cu 'h'. Daca magic nu este 'h' atunci se va returna un mesaj de
eroare si se iese din functie. Citesc dimensiunea antetului si versiunea fisierului, aceasta
actualizand cu valoarea citita variabila versiune transmisa ca parametru. Verficam daca valoarea
versiunii este cuprinsa intre 117 si 171, iar daca nu se va afisa un mesaj de eroare si
iesim din functie. Citesc numarul de sectiuni din fisier si verific daca este cuprins intre
4 si 16 iar daca nu se va afisa un mesaj de eroare si se incheie executia functiei. Citesc
fiecare sectiune a fisierului iar pentru fiecare dintre acestea se citesc campurile  name,
type, offset si size. Daca type nu este 26 sau 99 se afiseaza un mesaj de eroare si se incheie
executia functiei.
- Daca am trecut cu succes de toate verificarile afisez "SUCCESS" iar pe liniile urmatoare
informatiile citite din fisier: versiunea, numarul de sectiuni si informatii despre fiecare
sectiune.

*/

void parse(const char* path, Header *var, int* nr_sectiuni, int *versiune)
{
    char magic;
    int header_size;
    int fd=open(path,O_RDONLY);

    //cittesc din fd, in variabila magic, un octet
    read(fd,&magic,1); //citesc un octet

    if(magic!='h')  //fisierul e corupt
    {
        printf("ERROR\nwrong magic\n");
        return ;
    }

    read(fd,&header_size,2); //2 octeti
    read(fd,versiune,2);

    if(!(*versiune>=117&&*versiune<=171))
    {
        printf("ERROR\nwrong version\n");
        return ;
    }

    read(fd,nr_sectiuni,1);

    if(!(*nr_sectiuni>=4&&*nr_sectiuni<=16))
    {
        printf("ERROR\nwrong sect_nr\n");
        return ;
    }

    for(int i=0;i<*nr_sectiuni;i++) //citesc in structura
    {
        read(fd,var[i].name,20);
        var[i].name[20]='\0'; //ca sa stiu ca am \0 pun pe ultima deoarece daca nu crapa
        read(fd,&var[i].type,4);
        read(fd,&var[i].offset,4);
        read(fd,&var[i].size,4);

        if(var[i].type!=26 && var[i].type!=99)
        {
            printf("ERROR\nwrong sect_types\n");
            return ;
        }
    }

    printf("SUCCESS\n");
    printf("version=%d\n",*versiune);
    printf("nr_sections=%d\n",*nr_sectiuni);
    for(int i=0;i<*nr_sectiuni;i++)//afisez informatiile pentru fiecare sectiune
    {
        printf("section%d: %s %d %d\n",i+1,var[i].name,var[i].type,var[i].size);
    }
}

/**

- Aceasta functie face aproximativ acelasi lucru ca si functia parse insa nu mai afiseaza mesaje
de eroare sau de succes, sau alte informatii despre sectiuni, ci doar verifica daca parsarea
fisierului care are calea transmisa ca parametru functiei a fost efectuata cu succes. Daca
parsarea a fost efectuata cu succes se va afisa calea fisierului, iar daca nu, executia functiei
se va incheia atunci cand gaseste o conditie care nu e indeplinita.

*/

void parsare_f(const char* path)
{
    char magic;
    int header_size;
    Header var[200];
    int nr_sectiuni;
    int versiune;
    int fd=open(path,O_RDONLY);

    read(fd,&magic,1);

    if(magic!='h')
    {
        return ;
    }

    read(fd,&header_size,2);
    read(fd,&versiune,2);

    if(!(versiune>=117&&versiune<=171))
    {
        return ;
    }

    read(fd,&nr_sectiuni,1);

    if(!(nr_sectiuni>=4&&nr_sectiuni<=16))
    {
        return ;
    }

    for(int i=0;i<nr_sectiuni;i++)
    {
        read(fd,var[i].name,20);
        var[i].name[20]='\0';
        read(fd,&var[i].type,4);
        read(fd,&var[i].offset,4);
        read(fd,&var[i].size,4);

        if(var[i].type!=26 && var[i].type!=99)
        {
            return ;
        }

        if(var[i].size>=922)
        {
            return ;
        }
    }
    printf("%s\n",path);
}


/**

- Functia primeste calea directorului si o variabila afisat care va decide daca se
va afisa mesajul descris de stare, "ERROR" sau "SUCCESS".
- Prima data se deschide directorul si verifica daca acesta este valid, iar daca acesta
nu este valid se va afisa mesajul "invalid directory path", iar in caz contrar, daca acesta
este valid si daca nu afost afisata inca starea de succes, adica variabila afisat inca are
valoarea egala cu 0, atunci se afiseaza " SUCCESS".
- Pe urma se citesc fisierele din directorul curent, excluzand directorul curent "." si
directorul parinte "..", iar pentru fiecare fisier se creeaza o cale "fullPath" prin
concatenarea caii directorului si a numelui fisierelui.
- Cu functia lstat extragem informatii despre fisier. Verificam tipul fisierului prin conditiile
S_ISDIR(statbuf.st_mode) si S_ISREG(statbuf.st_mode), astfel determinam daca fisierul e director
sau registru.
- Daca este director apelam functia recursiv iar daca este fisier regulat apleam functia
parsare_f pentru acel fisier.

*/

void cautare(const char* dirPath, int afisat)
{
    DIR *dir = NULL;
    struct dirent *entry = NULL;
    char fullPath[512];
    struct stat statbuf;

    dir = opendir(dirPath);
    if(dir == NULL)
    {
        printf("ERROR\ninvalid directory path\n");
        return;
    }
    else
        if(afisat==0)
        {
            printf("SUCCESS\n");
            afisat=1;
        }
    while((entry = readdir(dir)) != NULL)
    {
        if(strcmp(entry->d_name, ".") != 0 && strcmp(entry->d_name, "..") != 0)
        {
            snprintf(fullPath, 512, "%s/%s", dirPath, entry->d_name);
            if(lstat(fullPath, &statbuf) == 0)
            {

                if(S_ISDIR(statbuf.st_mode))
                {
                    cautare(fullPath,afisat);
                }
                if(S_ISREG(statbuf.st_mode))
                {
                    parsare_f(fullPath);
                }
            }
        }
    }
    closedir(dir);
}


/**

- Definesc variabilele afisat, lista, parsare, recursivitate, size_value, name_starts_with,
cale_fisier, v, nr_sectiuni, si versiune. Initializez sirul name_starts_with cu '\0'.
- Daca programul a fost rulat cu argumentul "variant" se va afisa numarul 81948 care reprezinta
numarul variantei. In caz contrar, parcurc argumentele din linia de comanda cu for si daca se
gaseste unul dintre argumentele "list", "recursive", "parse" si "findall", variabilele, lista,
sau recursivitate, sau parsare, sau respectiv gasire vor lua valoarea 1.
- Daca se gaseste argumentul "path=" se copiaza valoarea acestuia in variabila cale_fisier incepand
cu al cincilea caracter deoarece primele cinci reprezinta caracterele din "path=". Si de asemenea
pun la finalul sirului caracterul terminator '\0';
- Daca se gaseste argumentul "size_greater=" se foloseste functia sscanf pentru a extrage numarul
care urmeaza dupa "size_greater=" si il pun in variabila size_value.
- Daca se gaseste argumentul "name_strats_with=" se copiaza valoarea acestuia in variabila
name_strats_with incepand cu al saptesprezecelea caracter deoarece primele saptesprezece caractere
reprezinta caracterele din "name_strats_with=". Si de asemenea pun la finalul sirului caracterul
terminator '\0';
- Dupa acestea verificam valorile variabilelor lista, recursivitate, parsare si gasire pentru a decide
ce actiuni se efectueaza. Daca atat lista cat si recursivitate au valoarea 1, se apeleaza functia
afisareRecursiva. Daca doar lista are valoarea 1 se apeleaza functia afisareSimpla. Daca parsare are
valoarea 1 se apeleaza functia parse si daca gasire are valoarea 1 se apeleaza functia cautare.

*/

int main(int argc, char **argv)
{
    int afisat=0;
    int lista=0;
    int parsare=0;
    int recursivitate=0;
    int gasire=0;
    int size_value=0;
    char name_starts_with[100];
    char cale_fisier[200];
    Header v[200];
    int nr_sectiuni=0;
    int versiune=0;

    name_starts_with[0]='\0';
    if(argc>=2)
    {
        if(strcmp(argv[1],"variant")==0)
            printf("81948\n");
        else
        {
            for(int i=1;i<argc;i++)
            {
                if(strcmp(argv[i],"list")==0)
                {
                    lista=1;
                }
                if(strcmp(argv[i],"recursive")==0)
                {
                    recursivitate=1;
                }
                if(strcmp(argv[i],"parse")==0)
                {
                    parsare=1;
                }
                if(strcmp(argv[i],"findall")==0)
                {
                    gasire=1;
                }
                if(strncmp(argv[i],"path=",5)==0)
                {
                    strcpy(cale_fisier,argv[i]+5);
                    strcat(cale_fisier,"\0");
                }

                if(strncmp(argv[i],"size_greater=",13)==0)
                {
                    sscanf(argv[i]+13,"%d",&size_value);
                }
                if(strncmp(argv[i],"name_starts_with=",17)==0)
                {
                    strcpy(name_starts_with,argv[i]+17);
                    strcat(name_starts_with,"\0");
                }
            }
            if(lista==1&&recursivitate==1)
            {
                afisareRecursiva(cale_fisier,afisat,size_value, name_starts_with);
            }
            else
                if(lista==1)
                {
                    afisareSimpla(cale_fisier,size_value, name_starts_with);
                }
                else
                    if(parsare==1)
                    {
                        parse(cale_fisier,v,&nr_sectiuni,&versiune);
                    }
                    else
                        if(gasire==1)
                        {
                            cautare(cale_fisier,afisat);
                        }
        }
    }
    return 0;
}
