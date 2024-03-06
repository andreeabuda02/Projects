using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SqlClient;

namespace Agentie_de_turism
{
    public partial class Rezervare : Form
    {
        SqlConnection con = new SqlConnection(@"Data Source=(LocalDB)\MSSQLLocalDB;AttachDbFilename=D:\CSharp\Agentie de turism\Agentie de turism\Database1.mdf;Integrated Security=True");
        SqlCommand cmd = new SqlCommand();
        SqlDataReader dr;

        int intoarcere = Agentie_de_turism.Properties.Settings.Default.Tara, aeroport, NrCamere, cameraN, numaraCamere, idCamera, idMaximCamera, idMinimCamera, afisareInfo = 0, idNumaraCamere, idCameraHotelMaxim, idCameraHotelMinim, idCameraHotel, idRezervareMaxim, idRezervareMinim, zileRezervari, idResetRezervareMinim, idResetRezervareMaxim, tipTr = 0;
        string[] camereAlese, numarareCamere;
        bool cameraGasita, idGasit;
        float pretTotal;

        public Rezervare(string Cont, string Continent, string Tara, string Oras, string Regiune)
        {
            InitializeComponent();
            textBoxCont.Text = Cont.ToString();
            textBoxContinent.Text = Continent.ToString();
            textBoxTara.Text = Tara.ToString();
            textBoxOras.Text = Oras.ToString();
            textBoxRegiune.Text = Regiune.ToString();
        }

        void stergereRezervari()
        {
            idResetRezervareMaxim = 0;
            idResetRezervareMinim = 1;
            idRezervareMaxim = 0;
            idRezervareMinim = 1;
            con.Open();
            cmd.CommandText = "select max(id) from rezervari";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    textBoxIdRezervareMaxim.Text = dr[0].ToString();
            con.Close();
            if (textBoxIdRezervareMaxim.Text != "")
            {
                idRezervareMaxim = int.Parse(textBoxIdRezervareMaxim.Text);
                con.Open();
                cmd.CommandText = "select min(id) from rezervari";
                dr = cmd.ExecuteReader();
                if (dr.HasRows)
                    while (dr.Read())
                        idRezervareMinim = int.Parse(dr[0].ToString());
                con.Close();
                for (int i = idRezervareMinim; i <= idRezervareMaxim; i++)
                {
                    con.Open();
                    cmd.CommandText = "select data from rezervari where id='" + i.ToString() + "'";
                    dr = cmd.ExecuteReader();
                    if (dr.HasRows)
                        while (dr.Read())
                            textBoxCamereAlese.Text = dr[0].ToString();
                    con.Close();
                    dateTimePickerDataStergere.Value = Convert.ToDateTime(textBoxCamereAlese.Text);
                    con.Open();
                    cmd.CommandText = "select nr_zile from rezervari where id='" + i.ToString() + "'";
                    dr = cmd.ExecuteReader();
                    if (dr.HasRows)
                        while (dr.Read())
                            zileRezervari = int.Parse(dr[0].ToString());
                    con.Close();
                    if (dateTimePickerDataStergere.Value.AddDays(zileRezervari) <= DateTime.Today)
                    {
                        con.Open();
                        cmd.CommandText = "insert into resetrezervari(data, nr_zile, id_camera, id_client, id_tip_transport, id_hotel, tip_bilet, id_distanta) values((select data from rezervari where id='" + i.ToString() + "'), (select nr_zile from rezervari where id='" + i.ToString() + "'), (select id_camera from rezervari where id='" + i.ToString() + "'), (select id_client from rezervari where id='" + i.ToString() + "'), (select id_tip_transport from rezervari where id='" + i.ToString() + "'), (select id_hotel from rezervari where id='" + i.ToString() + "'), (select tip_bilet from rezervari where id='" + i.ToString() + "'), (select id_distanta from rezervari where id='" + i.ToString() + "'))";
                        cmd.ExecuteNonQuery();
                        con.Close();
                        con.Open();
                        cmd.CommandText = "delete from rezervari where id='" + i.ToString() + "'";
                        cmd.ExecuteNonQuery();
                        con.Close();
                    }
                    else
                    {
                        con.Open();
                        cmd.CommandText = "insert into resetrezervari(data, nr_zile, id_camera, id_client, id_tip_transport, id_hotel, tip_bilet, id_distanta) values('" + dateTimePickerDataStergere.Text + "', '" + zileRezervari.ToString() + "', (select id_camera from rezervari where id='" + i.ToString() + "'), (select id_client from rezervari where id='" + i.ToString() + "'), (select id_tip_transport from rezervari where id='" + i.ToString() + "'), (select id_hotel from rezervari where id='" + i.ToString() + "'), (select tip_bilet from rezervari where id='" + i.ToString() + "'), (select id_distanta from rezervari where id='" + i.ToString() + "'))";
                        cmd.ExecuteNonQuery();
                        con.Close();
                    }
                    
                }
                con.Open();
                cmd.CommandText = "truncate table rezervari";
                cmd.ExecuteNonQuery();
                con.Close();
                con.Open();
                cmd.CommandText = "select max(id) from resetrezervari";
                dr = cmd.ExecuteReader();
                if (dr.HasRows)
                    while (dr.Read())
                        textBoxIdRezervareMaxim.Text = dr[0].ToString();
                con.Close();
                if(textBoxIdRezervareMaxim.Text != "")
                {
                    idResetRezervareMaxim = int.Parse(textBoxIdRezervareMaxim.Text);
                    con.Open();
                    cmd.CommandText = "select min(id) from resetrezervari";
                    dr = cmd.ExecuteReader();
                    if (dr.HasRows)
                        while (dr.Read())
                            idResetRezervareMinim = int.Parse(dr[0].ToString());
                    con.Close();
                    for (int i = idResetRezervareMinim; i <= idResetRezervareMaxim; i++)
                    {
                        con.Open();
                        cmd.CommandText = "insert into rezervari(data, nr_zile, id_camera, id_client, id_tip_transport, id_hotel, tip_bilet, id_distanta) values((select data from resetrezervari where id='" + i.ToString() + "'), (select nr_zile from resetrezervari where id='" + i.ToString() + "'), (select id_camera from resetrezervari where id='" + i.ToString() + "'), (select id_client from resetrezervari where id='" + i.ToString() + "'), (select id_tip_transport from resetrezervari where id='" + i.ToString() + "'), (select id_hotel from resetrezervari where id='" + i.ToString() + "'), (select tip_bilet from resetrezervari where id='" + i.ToString() + "'), (select id_distanta from resetrezervari where id='" + i.ToString() + "'))";
                        cmd.ExecuteNonQuery();
                        con.Close();
                    }
                }
                con.Open();
                cmd.CommandText = "truncate table resetrezervari";
                cmd.ExecuteNonQuery();
                con.Close();
            }
        }

        void pozitieButoaneInceput()
        {
            comboBoxTipBilet.Location = new Point(panelRezervare.Width / 2 - comboBoxTipBilet.Width / 2 + panelRezervare.Location.X, panelRezervare.Height / 2 - comboBoxTipBilet.Height / 2 + panelRezervare.Location.Y);
            comboBoxTipTransport.Location = new Point(comboBoxTipBilet.Location.X, comboBoxTipBilet.Location.Y - 30);
            textBoxEmail.Location = new Point(comboBoxTipTransport.Location.X, comboBoxTipTransport.Location.Y - 28);
            textBoxTelefon.Location = new Point(textBoxEmail.Location.X, textBoxEmail.Location.Y - 28);
            textBoxNume.Location = new Point(textBoxTelefon.Location.X, textBoxTelefon.Location.Y - 28);
            textBoxPrenume.Location = new Point(textBoxNume.Location.X, textBoxNume.Location.Y - 28);
            comboBoxPornire.Location = new Point(comboBoxTipBilet.Location.X - 80, comboBoxTipBilet.Location.Y + 50);
            comboBoxSosire.Location = new Point(comboBoxPornire.Location.X, comboBoxPornire.Location.Y + 58);
            comboBoxAeroportClient.Location = new Point(comboBoxPornire.Location.X + 147, comboBoxPornire.Location.Y);
            comboBoxAeroportHotel.Location = new Point(comboBoxSosire.Location.X + 147, comboBoxSosire.Location.Y);
            textBoxDistantaClientAeroport.Location = new Point(comboBoxAeroportClient.Location.X + 150, comboBoxAeroportClient.Location.Y);
            textBoxDistantaHotelAeroport.Location = new Point(comboBoxAeroportHotel.Location.X + 150, comboBoxAeroportHotel.Location.Y);
            comboBoxHotel.Location = new Point(comboBoxTipBilet.Location.X, comboBoxTipBilet.Location.Y + 152);
            comboBoxCameraHotel.Location = new Point(comboBoxHotel.Location.X, comboBoxHotel.Location.Y + 30);
            textBoxNrCamere.Location = new Point(comboBoxCameraHotel.Location.X - 29, comboBoxCameraHotel.Location.Y);
            textBoxCapacitate.Location = new Point(comboBoxCameraHotel.Location.X + 160, comboBoxCameraHotel.Location.Y);
            textBoxCamereDisponibile.Location = new Point(textBoxCapacitate.Location.X + 50, textBoxCapacitate.Location.Y);
            buttonStergeCamera.Location = new Point(comboBoxCameraHotel.Location.X, comboBoxCameraHotel.Location.Y + 30);
            buttonUrmatoareaCamera.Location = new Point(buttonStergeCamera.Location.X + 85, buttonStergeCamera.Location.Y);
            dateTimePickerDataRezervare.Location = new Point(comboBoxCameraHotel.Location.X, comboBoxCameraHotel.Location.Y + 85);
            textBoxNrZile.Location = new Point(dateTimePickerDataRezervare.Location.X - 29, dateTimePickerDataRezervare.Location.Y);
            labelPrenume.Location = new Point(textBoxPrenume.Location.X - 63, textBoxPrenume.Location.Y + 3);
            labelNume.Location = new Point(textBoxNume.Location.X - 64, textBoxNume.Location.Y + 3);
            labelTelefon.Location = new Point(textBoxTelefon.Location.X - 43, textBoxTelefon.Location.Y + 3);
            labelEmail.Location = new Point(textBoxEmail.Location.X - 38, textBoxEmail.Location.Y + 3);
            labelTipTransport.Location = new Point(comboBoxTipTransport.Location.X - 80, comboBoxTipTransport.Location.Y + 3);
            labelTipBilet.Location = new Point(comboBoxTipBilet.Location.X - 66, comboBoxTipBilet.Location.Y + 3);
            labelHotel.Location = new Point(comboBoxHotel.Location.X - 40, comboBoxHotel.Location.Y + 3);
            labelCamereAlese.Location = new Point(textBoxNrCamere.Location.X - 150, textBoxNrCamere.Location.Y + 3);
            labelCamereDisponibile.Location = new Point(textBoxCamereDisponibile.Location.X + 27, textBoxCamereDisponibile.Location.Y + 3);
            labelDataRezervare.Location = new Point(textBoxNrZile.Location.X - 135, textBoxNrZile.Location.Y + 3);
            labelCapacitate.Location = new Point(textBoxCapacitate.Location.X - 15, textBoxCapacitate.Location.Y - 20);
            labelDistantaClientAeroport.Location = new Point(textBoxDistantaClientAeroport.Location.X, textBoxDistantaClientAeroport.Location.Y - 20);
            labelDistantaHotelAeroport.Location = new Point(textBoxDistantaHotelAeroport.Location.X, textBoxDistantaHotelAeroport.Location.Y - 20);
            labelPornire.Location = new Point(comboBoxPornire.Location.X + 30, comboBoxPornire.Location.Y - 20);
            labelSosire.Location = new Point(comboBoxSosire.Location.X + 35, comboBoxSosire.Location.Y - 20);
            labelAeroportClient.Location = new Point(comboBoxAeroportClient.Location.X + 45, comboBoxAeroportClient.Location.Y - 20);
            labelAeroportHotel.Location = new Point(comboBoxAeroportHotel.Location.X + 45, comboBoxAeroportHotel.Location.Y - 20);
            buttonReincarcare.Location = new Point(labelCapacitate.Location.X, textBoxPrenume.Location.Y - 4);
            labelAeroportApropiatClient.Location = new Point(buttonReincarcare.Location.X, buttonReincarcare.Location.Y + 50);
            labelAeroportApropiatHotel.Location = new Point(labelAeroportApropiatClient.Location.X + 2, labelAeroportApropiatClient.Location.Y + 28);
            textBoxAeroportClientApropiat.Location = new Point(labelAeroportApropiatClient.Location.X + 100, labelAeroportApropiatClient.Location.Y - 3);
            textBoxAeroportHotelApropiat.Location = new Point(labelAeroportApropiatHotel.Location.X + 98, labelAeroportApropiatHotel.Location.Y - 3);
            buttonInformatiiRezervare.Location = new Point(buttonReincarcare.Location.X + 60, buttonReincarcare.Location.Y);
        }

        void ascundereButoaneInceput()
        {
            textBoxCont.Hide();
            textBoxContinent.Hide();
            textBoxTara.Hide();
            textBoxRegiune.Hide();
            textBoxOras.Hide();
            buttonRezervare.Hide();
            comboBoxPornire.Hide();
            labelPornire.Hide();
            comboBoxAeroportClient.Hide();
            labelAeroportClient.Hide();
            textBoxDistantaClientAeroport.Hide();
            labelDistantaClientAeroport.Hide();
            comboBoxSosire.Hide();
            labelSosire.Hide();
            comboBoxAeroportHotel.Hide();
            labelAeroportHotel.Hide();
            textBoxDistantaHotelAeroport.Hide();
            labelDistantaHotelAeroport.Hide();
            comboBoxTipBilet.Hide();
            labelTipBilet.Hide();
            comboBoxHotel.Hide();
            labelHotel.Hide();
            textBoxNrCamere.Hide();
            comboBoxCameraHotel.Hide();
            labelCamereAlese.Hide();
            textBoxCapacitate.Hide();
            labelCapacitate.Hide();
            textBoxCamereDisponibile.Hide();
            labelCamereDisponibile.Hide();
            buttonStergeCamera.Hide();
            buttonUrmatoareaCamera.Hide();
            textBoxNrZile.Hide();
            dateTimePickerDataRezervare.Hide();
            labelDataRezervare.Hide();
            textBoxCameraIndisponibila.Hide();
            textBoxCamereAlese.Hide();
            textBoxSelectieCamereHotel.Hide();
            textBoxNumaraCamere.Hide();
            labelAeroportApropiatClient.Hide();
            labelAeroportApropiatHotel.Hide();
            textBoxAeroportClientApropiat.Hide();
            textBoxAeroportHotelApropiat.Hide();
            textBoxDistantaClientAeroport2.Hide();
            textBoxDistantaHotelAeroport2.Hide();
            buttonInformatiiRezervare.Hide();
            textBoxAfisareCamereAlese.Hide();
            textBoxDistantaInformatii.Hide();
            textBoxPretCamera.Hide();
            dateTimePickerDataStergere.Hide();
            textBoxIdRezervareMaxim.Hide();
            textBoxIdDistanta.Hide();
            textBoxAeroportClientApropiat.BackColor = Color.White;
            textBoxAeroportHotelApropiat.BackColor = Color.White;
            textBoxNrCamere.Enabled = false;
            comboBoxCameraHotel.Enabled = false;
            dateTimePickerDataPornire.Enabled = false;
            dateTimePickerPlecare.Enabled = false;
            textBoxNrCamere.BackColor = Color.White;
            comboBoxCameraHotel.BackColor = Color.White;
            textBoxPrenume.BackColor = Color.White;
            textBoxNume.BackColor = Color.White;
            textBoxEmail.BackColor = Color.White;
            textBoxTelefon.BackColor = Color.White;
            textBoxCapacitate.BackColor = Color.White;
            textBoxCamereDisponibile.BackColor = Color.White;
            textBoxDistantaClientAeroport.BackColor = Color.White;
            textBoxDistantaHotelAeroport.BackColor = Color.White;
            textBoxNrCamere.ForeColor = Color.Black;
            comboBoxCameraHotel.ForeColor = Color.Black;
            textBoxPrenume.ForeColor = Color.Black;
            textBoxNume.ForeColor = Color.Black;
            textBoxEmail.ForeColor = Color.Black;
            textBoxTelefon.ForeColor = Color.Black;
            textBoxCapacitate.ForeColor = Color.Black;
            textBoxCamereDisponibile.ForeColor = Color.Black;
            textBoxDistantaClientAeroport.ForeColor = Color.Black;
            textBoxDistantaHotelAeroport.ForeColor = Color.Black;
        }

        void incarcareDateInceput()
        {
            cameraN = 0;
            NrCamere = 0;
            textBoxNrZile.Text = "";
            textBoxNrCamere.Text = "";
            textBoxCapacitate.Text = "";
            textBoxCamereDisponibile.Text = "";
            dateTimePickerDataRezervare.Value = DateTime.Today;
            if(tipTr == 0)
                comboBoxTipTransport.Items.Clear();
            comboBoxPornire.Items.Clear();
            comboBoxAeroportClient.Items.Clear();
            comboBoxSosire.Items.Clear();
            comboBoxAeroportHotel.Items.Clear();
            comboBoxHotel.Items.Clear();
            comboBoxCameraHotel.Items.Clear();
            textBoxAeroportClientApropiat.Text = "";
            textBoxAeroportHotelApropiat.Text = "";
            con.Open();
            cmd.CommandText = "select prenume from clienti where email='" + textBoxCont.Text + "'";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    textBoxPrenume.Text = dr[0].ToString();
            con.Close();
            con.Open();
            cmd.CommandText = "select nume from clienti where email='" + textBoxCont.Text + "'";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    textBoxNume.Text = dr[0].ToString();
            con.Close();
            con.Open();
            cmd.CommandText = "select telefon from clienti where email='" + textBoxCont.Text + "'";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    textBoxTelefon.Text = dr[0].ToString();
            con.Close();
            textBoxEmail.Text = textBoxCont.Text;
            if(tipTr == 0)
            {
                con.Open();
                cmd.CommandText = "select tip from tip_transporturi";
                dr = cmd.ExecuteReader();
                if (dr.HasRows)
                    while (dr.Read())
                        comboBoxTipTransport.Items.Add(dr[0].ToString());
                con.Close();
            }
            con.Open();
            cmd.CommandText = "select (select localitate from localitati where id=id_localitate) from clienti where email='" + textBoxCont.Text + "'";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxPornire.Text = dr[0].ToString();
            con.Close();
            con.Open();
            cmd.CommandText = "select localitate from localitati";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxPornire.Items.Add(dr[0].ToString());
            con.Close();
            comboBoxSosire.Text = textBoxOras.Text;
            con.Open();
            cmd.CommandText = "select localitate from localitati where id_regiune in (select id from regiuni where regiune='" + textBoxRegiune.Text + "' and id_tara=(select id from tari where tara='" + textBoxTara.Text + "'))";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxSosire.Items.Add(dr[0].ToString());
            con.Close();
            con.Open();
            cmd.CommandText = "select min(distanta) from distante where id_loc1=(select id from localitati where localitate='" + comboBoxPornire.Text + "') and id_loc2 in (select id_localitate from aeroporturi)";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    textBoxDistantaClientAeroport.Text = dr[0].ToString();
            con.Close();
            con.Open();
            cmd.CommandText = "select (select localitate from localitati where id=id_loc2) from distante where id_loc1=(select id from localitati where localitate='" + comboBoxPornire.Text + "') and distanta='" + textBoxDistantaClientAeroport.Text + "' and id_loc2 in (select id_localitate from aeroporturi)";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxAeroportClient.Text = dr[0].ToString();
            con.Close();
            con.Open();
            cmd.CommandText = "select localitate from localitati where id in (select id_localitate from aeroporturi)";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxAeroportClient.Items.Add(dr[0].ToString());
            con.Close();
            con.Open();
            cmd.CommandText = "select min(distanta) from distante where id_loc1=(select id from localitati where localitate='" + comboBoxSosire.Text + "') and id_loc2 in (select id_localitate from aeroporturi)";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    textBoxDistantaHotelAeroport.Text = dr[0].ToString();
            con.Close();
            con.Open();
            cmd.CommandText = "select (select localitate from localitati where id=id_loc2) from distante where id_loc1=(select id from localitati where localitate='" + comboBoxSosire.Text + "') and distanta='" + textBoxDistantaHotelAeroport.Text + "' and id_loc2 in (select id_localitate from aeroporturi)";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxAeroportHotel.Text = dr[0].ToString();
            con.Close();
            con.Open();
            cmd.CommandText = "select localitate from localitati where id in (select id_localitate from aeroporturi) and id_regiune in (select id from regiuni where regiune='" + textBoxRegiune.Text + "' and id_tara in (select id from tari where tara='" + textBoxTara.Text + "'))";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxAeroportHotel.Items.Add(dr[0].ToString());
            con.Close();
            con.Open();
            cmd.CommandText = "select denumire from hoteluri where id_localitate in (select id from localitati where localitate='" + textBoxOras.Text + "' and id_regiune in (select id from regiuni where regiune='" + textBoxRegiune.Text + "' and id_tara in (select id from tari where tara='" + textBoxTara.Text + "')))";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxHotel.Items.Add(dr[0].ToString());
            con.Close();
            textBoxAeroportClientApropiat.Text = comboBoxAeroportClient.Text;
            textBoxAeroportHotelApropiat.Text = comboBoxAeroportHotel.Text;
            comboBoxHotel.Text = "Select a hotel";
            comboBoxCameraHotel.Text = "Select a room";
            comboBoxTipTransport.Text = "Select a transport type";
            comboBoxTipBilet.Text = "Select a ticket type";
        }

        void stergeInformatii()
        {
            textBoxTipBilet.Text = "";
            textBoxPretBilet.Text = "";
            textBoxPretTotal.Text = "";
            dateTimePickerDataPornire.Value = dateTimePickerDataRezervare.Value;
            dateTimePickerPlecare.Value = dateTimePickerDataRezervare.Value;
            labelCamere.Text = "";
            panelInformatiiRezervare.Hide();
            afisareInfo = 0;
        }

        void afisareButoaneInformatiiRezervare()
        {
            if (aeroport == 1)
            {
                textBoxPretTotal.Show();
                textBoxPretBilet.Show();
                textBoxTipBilet.Show();
                dateTimePickerDataPornire.Show();
                dateTimePickerPlecare.Show();
                labelCamere.Show();
                labelPretTotal.Show();
                labelPretBilet.Show();
                labelTipBilet2.Show();
                labelDataPornire.Show();
                labelDataPlecare.Show();
                labelCamereSelectate.Show();
            }
            else
            {
                textBoxPretTotal.Show();
                textBoxTipBilet.Show();
                dateTimePickerDataPornire.Show();
                dateTimePickerPlecare.Show();
                labelCamere.Show();
                labelPretTotal.Show();
                labelTipBilet2.Show();
                labelDataPornire.Show();
                labelDataPlecare.Show();
                labelCamereSelectate.Show();
            }
        }
        
        void incarcareInformatiiRezervare()
        {
            pretTotal = 0;
            labelCamere.Text = "";
            if(aeroport == 1)
            {
                if (comboBoxTipBilet.Text != "Select a ticket type")
                {
                    textBoxTipBilet.Text = comboBoxTipBilet.Text;
                    if(comboBoxAeroportClient.Text != "Select an airport" && comboBoxAeroportHotel.Text != "Select an airport")
                    {
                        con.Open();
                        cmd.CommandText = "select distanta from distante where id_loc1=(select id from localitati where localitate='" + comboBoxAeroportClient.Text + "') and id_loc2=(select id from localitati where localitate='" + comboBoxAeroportHotel.Text + "')";
                        dr = cmd.ExecuteReader();
                        if (dr.HasRows)
                            while (dr.Read())
                                textBoxDistantaInformatii.Text = dr[0].ToString();
                        con.Close();
                        con.Open();
                        cmd.CommandText = "select pret_km from tip_transporturi where tip='" + comboBoxTipTransport.Text + "'";
                        dr = cmd.ExecuteReader();
                        if (dr.HasRows)
                            while (dr.Read())
                                textBoxPretBilet.Text = dr[0].ToString();
                        con.Close();
                        textBoxPretBilet.Text = (float.Parse(textBoxPretBilet.Text) * float.Parse(textBoxDistantaInformatii.Text)).ToString();
                        if (comboBoxTipBilet.Text == "Round trip ticket")
                            textBoxPretBilet.Text = (float.Parse(textBoxPretBilet.Text) * 2).ToString();
                    }
                }
                else
                    textBoxTipBilet.Text = "No ticket type selected yet";
                numarareCamere = null;
                camereAlese = null;
                textBoxAfisareCamereAlese.Text = textBoxCamereAlese.Text;
                camereAlese = textBoxAfisareCamereAlese.Text.Split('_');
                numarareCamere = textBoxAfisareCamereAlese.Text.Split('_');
                int k;
                for(int i = 0; i < cameraN - 1; i++)
                {
                    k = 0;
                    for(int j = 0; j < cameraN - 1; j++)
                        if(numarareCamere[j] != "0")
                            if(numarareCamere[j] == camereAlese[i])
                            {
                                k++;
                                numarareCamere[j] = "0";
                            }
                    if (k > 0)
                    {
                        con.Open();
                        cmd.CommandText = "select pret from tip_camere where tip='" + camereAlese[i] + "'";
                        dr = cmd.ExecuteReader();
                        if (dr.HasRows)
                            while (dr.Read())
                                textBoxPretCamera.Text = dr[0].ToString();
                        con.Close();
                        labelCamere.Text += camereAlese[i] + " - " + k.ToString() + "*" + textBoxPretCamera.Text + "(€)/night" + " -> " + k.ToString() + "\n";
                        pretTotal += float.Parse(textBoxPretCamera.Text) * k;
                    }
                }
                dateTimePickerDataPornire.Value = dateTimePickerDataRezervare.Value;
                if (textBoxNrZile.Text != "")
                {
                    dateTimePickerPlecare.Value = dateTimePickerDataPornire.Value.AddDays(int.Parse(textBoxNrZile.Text));
                    if(int.Parse(textBoxNrZile.Text) > 1)
                        pretTotal = pretTotal * (int.Parse(textBoxNrZile.Text) - 1);
                    else
                        pretTotal = pretTotal * (int.Parse(textBoxNrZile.Text));
                }
                else
                    dateTimePickerPlecare.Value = dateTimePickerDataPornire.Value;
                if (textBoxTipBilet.Text != "No ticket type selected yet")
                    pretTotal += float.Parse(textBoxPretBilet.Text);
                textBoxPretTotal.Text = pretTotal.ToString();
            }
            else
            {
                textBoxTipBilet.Text = "No ticket";
                textBoxPretBilet.Text = "0";
                numarareCamere = null;
                camereAlese = null;
                textBoxAfisareCamereAlese.Text = textBoxCamereAlese.Text;
                camereAlese = textBoxAfisareCamereAlese.Text.Split('_');
                numarareCamere = textBoxAfisareCamereAlese.Text.Split('_');
                int k;
                for (int i = 0; i < cameraN - 1; i++)
                {
                    k = 0;
                    for (int j = 0; j < cameraN - 1; j++)
                        if (numarareCamere[j] != "0")
                            if (numarareCamere[j] == camereAlese[i])
                            {
                                k++;
                                numarareCamere[j] = "0";
                            }
                    if (k > 0)
                    {
                        con.Open();
                        cmd.CommandText = "select pret from tip_camere where tip='" + camereAlese[i] + "'";
                        dr = cmd.ExecuteReader();
                        if (dr.HasRows)
                            while (dr.Read())
                                textBoxPretCamera.Text = dr[0].ToString();
                        con.Close();
                        labelCamere.Text += camereAlese[i] + " - " + k.ToString() + "*" + textBoxPretCamera.Text + "(€)/night" + " -> " + k.ToString() + "\n";
                        pretTotal += float.Parse(textBoxPretCamera.Text) * k;
                    }
                }
                dateTimePickerDataPornire.Value = dateTimePickerDataRezervare.Value;
                if (textBoxNrZile.Text != "")
                {
                    dateTimePickerPlecare.Value = dateTimePickerDataPornire.Value.AddDays(int.Parse(textBoxNrZile.Text));
                    if (int.Parse(textBoxNrZile.Text) > 1)
                        pretTotal = pretTotal * (int.Parse(textBoxNrZile.Text) - 1);
                    else
                        pretTotal = pretTotal * (int.Parse(textBoxNrZile.Text));
                }
                else
                    dateTimePickerPlecare.Value = dateTimePickerDataPornire.Value;
                textBoxPretTotal.Text = pretTotal.ToString();
            }
        }

        private void Rezervare_Load(object sender, EventArgs e)
        {
            cmd.Connection = con;
            FormBorderStyle = FormBorderStyle.None;
            WindowState = FormWindowState.Maximized;
            panelRezervare.Dock = DockStyle.Fill;
            dateTimePickerDataRezervare.MinDate = DateTime.Today;
            stergereRezervari();
            pozitieButoaneInceput();
            ascundereButoaneInceput();
            incarcareDateInceput();
            buttonRezervare.Location = new Point(panelRezervare.Width / 2 - buttonRezervare.Width / 2 + panelRezervare.Location.X, dateTimePickerDataRezervare.Location.Y + 47);
            labelTextRezervare.Location = new Point(panelRezervare.Width / 2 - labelTextRezervare.Width / 2 + panelRezervare.Location.X, textBoxPrenume.Location.Y - 50);
            panelInformatiiRezervare.Location = new Point(labelTextRezervare.Location.X - 600, labelTextRezervare.Location.Y);
            panelInformatiiRezervare.Hide();
            stergeInformatii();
            panelRezervare.BackgroundImage = Agentie_de_turism.Properties.Resources.Rezervare;
        }

        private void comboBoxHotel_SelectedIndexChanged(object sender, EventArgs e)
        {
            comboBoxCameraHotel.Items.Clear();
            textBoxNrCamere.Enabled = true;
            con.Open();
            cmd.CommandText = "select distinct (select tip from tip_camere where id=id_tip_camera) from camere where id_hotel in (select id from hoteluri where denumire='" + comboBoxHotel.Text + "')";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxCameraHotel.Items.Add(dr[0].ToString());
            con.Close();
            comboBoxCameraHotel.Enabled = false;
            comboBoxCameraHotel.Text = "Select a room";
            comboBoxCameraHotel.BackColor = Color.White;
            comboBoxCameraHotel.ForeColor = Color.Black;
            textBoxNrCamere.Text = "";
            buttonUrmatoareaCamera.Hide();
            buttonStergeCamera.Hide();
            buttonRezervare.Hide();
            textBoxCamereAlese.Text = "";
            textBoxCapacitate.Text = "";
            textBoxCamereDisponibile.Text = "";
            incarcareInformatiiRezervare();
        }

        private void comboBoxTipTransport_KeyPress(object sender, KeyPressEventArgs e)
        {
            e.Handled = true;
        }

        private void comboBoxPornire_KeyPress(object sender, KeyPressEventArgs e)
        {
            e.Handled = true;
        }

        private void comboBoxAeroportClientApropiat_KeyPress(object sender, KeyPressEventArgs e)
        {
            e.Handled = true;
        }

        private void comboBoxSosire_KeyPress(object sender, KeyPressEventArgs e)
        {
            e.Handled = true;
        }

        private void comboBoxAeroportHotelApropiat_KeyPress(object sender, KeyPressEventArgs e)
        {
            e.Handled = true;
        }

        private void comboBoxHotel_KeyPress(object sender, KeyPressEventArgs e)
        {
            e.Handled = true;
        }

        private void comboBoxCameraHotel_KeyPress(object sender, KeyPressEventArgs e)
        {
            e.Handled = true;
        }

        void pozitieButoane2()
        {
            comboBoxHotel.Location = new Point(comboBoxTipBilet.Location.X, comboBoxTipBilet.Location.Y + 30);
            comboBoxCameraHotel.Location = new Point(comboBoxHotel.Location.X, comboBoxHotel.Location.Y + 30);
            textBoxNrCamere.Location = new Point(comboBoxCameraHotel.Location.X - 29, comboBoxCameraHotel.Location.Y);
            textBoxCapacitate.Location = new Point(comboBoxCameraHotel.Location.X + 160, comboBoxCameraHotel.Location.Y);
            textBoxCamereDisponibile.Location = new Point(textBoxCapacitate.Location.X + 50, textBoxCapacitate.Location.Y);
            buttonStergeCamera.Location = new Point(comboBoxCameraHotel.Location.X, comboBoxCameraHotel.Location.Y + 30);
            buttonUrmatoareaCamera.Location = new Point(buttonStergeCamera.Location.X + 85, buttonStergeCamera.Location.Y);
            dateTimePickerDataRezervare.Location = new Point(comboBoxCameraHotel.Location.X, comboBoxCameraHotel.Location.Y + 85);
            textBoxNrZile.Location = new Point(dateTimePickerDataRezervare.Location.X - 29, dateTimePickerDataRezervare.Location.Y);
            labelHotel.Location = new Point(comboBoxHotel.Location.X - 40, comboBoxHotel.Location.Y + 3);
            labelCamereAlese.Location = new Point(textBoxNrCamere.Location.X - 150, textBoxNrCamere.Location.Y + 3);
            labelCamereDisponibile.Location = new Point(textBoxCamereDisponibile.Location.X + 27, textBoxCamereDisponibile.Location.Y + 3);
            labelDataRezervare.Location = new Point(textBoxNrZile.Location.X - 135, textBoxNrZile.Location.Y + 3);
            labelCapacitate.Location = new Point(textBoxCapacitate.Location.X - 15, textBoxCapacitate.Location.Y - 20);
            labelDistantaClientAeroport.Location = new Point(textBoxDistantaClientAeroport.Location.X, textBoxDistantaClientAeroport.Location.Y - 20);
            labelDistantaHotelAeroport.Location = new Point(textBoxDistantaHotelAeroport.Location.X, textBoxDistantaHotelAeroport.Location.Y - 20);
            labelPornire.Location = new Point(comboBoxPornire.Location.X + 30, comboBoxPornire.Location.Y - 20);
            labelSosire.Location = new Point(comboBoxSosire.Location.X + 35, comboBoxSosire.Location.Y - 20);
            labelAeroportClient.Location = new Point(comboBoxAeroportClient.Location.X + 45, comboBoxAeroportClient.Location.Y - 20);
            labelAeroportHotel.Location = new Point(comboBoxAeroportHotel.Location.X + 45, comboBoxAeroportHotel.Location.Y - 20);
        }

        void afisareButoane()
        {
            if(aeroport == 1)
            {
                comboBoxTipBilet.Show();
                labelTipBilet.Show();
                comboBoxPornire.Show();
                comboBoxAeroportClient.Show();
                comboBoxSosire.Show();
                comboBoxAeroportHotel.Show();
                textBoxDistantaClientAeroport.Show();
                textBoxDistantaHotelAeroport.Show();
                labelPornire.Show();
                labelSosire.Show();
                labelAeroportClient.Show();
                labelAeroportHotel.Show();
                labelDistantaClientAeroport.Show();
                labelDistantaHotelAeroport.Show();
                comboBoxHotel.Show();
                labelHotel.Show();
                textBoxNrCamere.Show();
                comboBoxCameraHotel.Show();
                textBoxCapacitate.Show();
                textBoxCamereDisponibile.Show();
                labelCamereAlese.Show();
                labelCapacitate.Show();
                labelCamereDisponibile.Show();
                textBoxNrZile.Show();
                dateTimePickerDataRezervare.Show();
                labelDataRezervare.Show();
                textBoxAeroportClientApropiat.Show();
                textBoxAeroportHotelApropiat.Show();
                labelAeroportApropiatClient.Show();
                labelAeroportApropiatHotel.Show();
                pozitieButoaneInceput();
            }
            else
            {
                comboBoxTipBilet.Hide();
                labelTipBilet.Hide();
                comboBoxPornire.Hide();
                comboBoxAeroportClient.Hide();
                comboBoxSosire.Hide();
                comboBoxAeroportHotel.Hide();
                textBoxDistantaClientAeroport.Hide();
                textBoxDistantaHotelAeroport.Hide();
                labelPornire.Hide();
                labelSosire.Hide();
                labelAeroportClient.Hide();
                labelAeroportHotel.Hide();
                labelDistantaClientAeroport.Hide();
                labelDistantaHotelAeroport.Hide();
                textBoxAeroportClientApropiat.Hide();
                textBoxAeroportHotelApropiat.Hide();
                labelAeroportApropiatClient.Hide();
                labelAeroportApropiatHotel.Hide();
                comboBoxHotel.Show();
                labelHotel.Show();
                textBoxNrCamere.Show();
                comboBoxCameraHotel.Show();
                textBoxCapacitate.Show();
                textBoxCamereDisponibile.Show();
                labelCamereAlese.Show();
                labelCapacitate.Show();
                labelCamereDisponibile.Show();
                textBoxNrZile.Show();
                dateTimePickerDataRezervare.Show();
                labelDataRezervare.Show();
                pozitieButoane2();
            }
        }

        void curatare()
        {
            textBoxCameraIndisponibila.Text = "";
            textBoxCamereAlese.Text = "";
            comboBoxTipBilet.Text = "Select a ticket type";
            textBoxPretCamera.Text = "";
            textBoxDistantaInformatii.Text = "";
            textBoxIdDistanta.Text = "";
            textBoxSelectieCamereHotel.Text = "";
            textBoxNumaraCamere.Text = "";
            textBoxIdRezervareMaxim.Text = "";
            textBoxAfisareCamereAlese.Text = "";
            textBoxDistantaClientAeroport2.Text = "";
            textBoxDistantaHotelAeroport2.Text = "";
            incarcareDateInceput();
        }

        private void comboBoxTipTransport_SelectedIndexChanged(object sender, EventArgs e)
        {
            tipTr = 1;
            if (comboBoxTipTransport.Text == "Airplane")
                aeroport = 1;
            else
                aeroport = 0;
            buttonInformatiiRezervare.Show();
            curatare();
            afisareButoane();
            afisareButoaneInformatiiRezervare();
            incarcareInformatiiRezervare();
        }

        private void comboBoxCameraHotel_SelectedIndexChanged(object sender, EventArgs e)
        {
            idNumaraCamere = 0;
            numaraCamere = 0;
            idCameraHotel = idCameraHotelMinim;
            textBoxCameraIndisponibila.Text = comboBoxCameraHotel.Text;
            while (idCameraHotel <= idCameraHotelMaxim)
            {
                if (!textBoxSelectieCamereHotel.Text.Contains(idCameraHotel.ToString()))
                {
                    con.Open();
                    cmd.CommandText = "select id from camere where id='" + idCameraHotel.ToString() + "' and id_hotel in (select id from hoteluri where denumire='" + comboBoxHotel.Text + "') and id_tip_camera in (select id from tip_camere where tip='" + comboBoxCameraHotel.Text + "')";
                    dr = cmd.ExecuteReader();
                    if (dr.HasRows)
                        idNumaraCamere++;
                    con.Close();
                }
                idCameraHotel++;
            }
            if (cameraN < NrCamere)
            {
                camereAlese = null;
                camereAlese = textBoxCamereAlese.Text.Split('_');
                for (int i = 0; i < cameraN; i++)
                    if (camereAlese[i] == comboBoxCameraHotel.Text)
                        numaraCamere++;
                if (idNumaraCamere == numaraCamere)
                {
                    MessageBox.Show("There are not any '" + textBoxCameraIndisponibila.Text + "' rooms free to rezerve!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                    comboBoxCameraHotel.Text = "Select a room";
                    buttonUrmatoareaCamera.Enabled = false;
                }
                else
                {
                    buttonUrmatoareaCamera.Enabled = true;
                    con.Open();
                    cmd.CommandText = "select distinct locuri from tip_camere where tip='" + comboBoxCameraHotel.Text + "'";
                    dr = cmd.ExecuteReader();
                    if (dr.HasRows)
                        while (dr.Read())
                            textBoxCapacitate.Text = dr[0].ToString();
                    con.Close();
                    textBoxCamereDisponibile.Text = (idNumaraCamere - numaraCamere).ToString();
                }
            }
            else
            {
                if(cameraN == NrCamere)
                { 
                    camereAlese = null;
                    camereAlese = textBoxCamereAlese.Text.Split('_');
                    for (int i = 0; i < cameraN; i++)
                        if (camereAlese[i] == comboBoxCameraHotel.Text)
                            numaraCamere++;
                    if (idNumaraCamere == numaraCamere)
                    {
                        MessageBox.Show("There are not any '" + textBoxCameraIndisponibila.Text + "' rooms free to rezerve!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                        comboBoxCameraHotel.Text = "Select a room";
                    }
                    else
                    {
                        cameraN++;
                        comboBoxCameraHotel.Enabled = false;
                        textBoxCamereAlese.Text += comboBoxCameraHotel.Text;
                        incarcareInformatiiRezervare();
                        con.Open();
                        cmd.CommandText = "select distinct locuri from tip_camere where tip='" + comboBoxCameraHotel.Text + "'";
                        dr = cmd.ExecuteReader();
                        if (dr.HasRows)
                            while (dr.Read())
                                textBoxCapacitate.Text = dr[0].ToString();
                        con.Close();
                        textBoxCamereDisponibile.Text = (idNumaraCamere - numaraCamere).ToString();
                        if (aeroport == 1)
                        {
                            if (comboBoxTipBilet.Text != "Select a ticket type" && comboBoxAeroportClient.Text != "Select an airport" && comboBoxAeroportHotel.Text != "Select an airport" && textBoxNrZile.Text != "" && comboBoxCameraHotel.Text != "Select a room" && cameraN == NrCamere + 1)
                                buttonRezervare.Show();
                            else
                                buttonRezervare.Hide();
                        }
                        else
                        {
                            if (textBoxNrZile.Text != "" && comboBoxCameraHotel.Text != "Select a room" && cameraN == NrCamere + 1)
                                buttonRezervare.Show();
                            else
                                buttonRezervare.Hide();
                        }
                    }
                }
            }
        }

        private void buttonStergeCamera_Click(object sender, EventArgs e)
        {
            buttonRezervare.Hide();
            comboBoxCameraHotel.Enabled = true;
            camereAlese = null;
            camereAlese = textBoxCamereAlese.Text.Split('_');
            textBoxCamereAlese.Text = "";
            for (int i = 0; i < cameraN - 2; i++)
                textBoxCamereAlese.Text += camereAlese[i] + "_";
            comboBoxCameraHotel.Text = "Select a room";
            cameraN--;
            if (cameraN < 2)
                buttonStergeCamera.Hide();
            if (cameraN == NrCamere - 1)
                buttonUrmatoareaCamera.Show();
            textBoxCapacitate.Text = "";
            textBoxCamereDisponibile.Text = "";
            incarcareInformatiiRezervare();
        }

        private void buttonUrmatoareaCamera_Click(object sender, EventArgs e)
        {
            if(comboBoxCameraHotel.Text != "Select a room")
            {
                if (cameraN > 1)
                    textBoxCamereAlese.Text += comboBoxCameraHotel.Text + "_";
                else
                    textBoxCamereAlese.Text = comboBoxCameraHotel.Text + "_";
                comboBoxCameraHotel.Text = "Select a room";
                cameraN++;
                if (cameraN > 1)
                    buttonStergeCamera.Show();
                if (cameraN == NrCamere)
                    buttonUrmatoareaCamera.Hide();
                textBoxCapacitate.Text = "";
                textBoxCamereDisponibile.Text = "";
                incarcareInformatiiRezervare();
            }
        }

        private void dateTimePickerDataRezervare_ValueChanged(object sender, EventArgs e)
        {
            incarcareInformatiiRezervare();
        }

        private void comboBoxPornire_SelectedIndexChanged(object sender, EventArgs e)
        {
            buttonRezervare.Hide();
            comboBoxAeroportClient.Items.Clear();
            comboBoxAeroportClient.Text = "";
            textBoxDistantaClientAeroport.Text = "";
            con.Open();
            cmd.CommandText = "select (select localitate from localitati where id_loc2=id) from distante where id_loc1=(select id from localitati where localitate='" + comboBoxPornire.Text + "') and id_loc2 in (select id_localitate from aeroporturi)";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxAeroportClient.Items.Add(dr[0].ToString());
            con.Close();
            con.Open();
            cmd.CommandText = "select min(distanta) from distante where id_loc1=(select id from localitati where localitate='" + comboBoxPornire.Text + "') and id_loc2 in (select id_localitate from aeroporturi)";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    textBoxDistantaHotelAeroport2.Text = dr[0].ToString();
            con.Close();
            con.Open();
            cmd.CommandText = "select (select localitate from localitati where id=id_loc2) from distante where id_loc1=(select id from localitati where localitate='" + comboBoxPornire.Text + "') and distanta='" + textBoxDistantaClientAeroport2.Text + "' and id_loc2 in (select id_localitate from aeroporturi)";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    textBoxAeroportClientApropiat.Text = dr[0].ToString();
            con.Close();
            comboBoxAeroportClient.Text = "Select an airport";
            
        }

        private void textBoxNrZile_TextChanged(object sender, EventArgs e)
        {
            if (textBoxNrZile.Text.StartsWith("0"))
                textBoxNrZile.Text = "";
            if (aeroport == 1)
            {
                if (comboBoxTipBilet.Text != "Select a ticket type" && comboBoxAeroportClient.Text != "Select an airport" && comboBoxAeroportHotel.Text != "Select an airport" && textBoxNrZile.Text != "" && comboBoxCameraHotel.Text != "Select a room" && cameraN == NrCamere + 1)
                    buttonRezervare.Show();
                else
                    buttonRezervare.Hide();
            }
            else
            {
                if (textBoxNrZile.Text != "" && comboBoxCameraHotel.Text != "Select a room" && cameraN == NrCamere + 1)
                    buttonRezervare.Show();
                else
                    buttonRezervare.Hide();
            }
            incarcareInformatiiRezervare();
        }

        private void textBoxNrZile_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsDigit(e.KeyChar) && !char.IsControl(e.KeyChar))
                e.Handled = true;
            else
                e.Handled = false;
        }

        private void comboBoxTipBilet_SelectedIndexChanged(object sender, EventArgs e)
        {
            if(aeroport == 1)
            {
                if (comboBoxTipBilet.Text != "Select a ticket type" && comboBoxAeroportClient.Text != "Select an airport" && comboBoxAeroportHotel.Text != "Select an airport" && textBoxNrZile.Text != "" && comboBoxCameraHotel.Text != "Select a room" && cameraN == NrCamere + 1)
                    buttonRezervare.Show();
                else
                    buttonRezervare.Hide();
            }
            else
            {
                if (textBoxNrZile.Text != "" && comboBoxCameraHotel.Text != "Select a room" && cameraN == NrCamere + 1)
                    buttonRezervare.Show();
                else
                    buttonRezervare.Hide();
            }
            incarcareInformatiiRezervare();
        }

        private void buttonReincarcare_Click(object sender, EventArgs e)
        {
            tipTr = 0;
            pozitieButoaneInceput();
            ascundereButoaneInceput();
            incarcareDateInceput();
            stergeInformatii();
        }

        private void buttonInformatiiRezervare_Click(object sender, EventArgs e)
        {
            afisareInfo++;
            if (afisareInfo % 2 == 1)
                panelInformatiiRezervare.Show();
            else
            {
                panelInformatiiRezervare.Hide();
                afisareInfo = 0;
            }
        }

        private void comboBoxAeroportClientApropiat_SelectedIndexChanged(object sender, EventArgs e)
        {
            textBoxDistantaClientAeroport.Text = "";
            con.Open();
            cmd.CommandText = "select distanta from distante where id_loc1=(select id from localitati where localitate='" + comboBoxPornire.Text + "') and id_loc2=(select id from localitati where localitate='" + comboBoxAeroportClient.Text + "')";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    textBoxDistantaClientAeroport.Text = dr[0].ToString();
            con.Close();
            if (aeroport == 1)
            {
                if (comboBoxTipBilet.Text != "Select a ticket type" && comboBoxAeroportClient.Text != "Select an airport" && comboBoxAeroportHotel.Text != "Select an airport" && textBoxNrZile.Text != "" && comboBoxCameraHotel.Text != "Select a room" && cameraN == NrCamere + 1)
                    buttonRezervare.Show();
                else
                    buttonRezervare.Hide();
            }
            else
            {
                if (textBoxNrZile.Text != "" && comboBoxCameraHotel.Text != "Select a room" && cameraN == NrCamere + 1)
                    buttonRezervare.Show();
                else
                    buttonRezervare.Hide();
            }
            incarcareInformatiiRezervare();
        }

        private void buttonRezervare_Click(object sender, EventArgs e)
        {
            camereAlese = null;
            camereAlese = textBoxCamereAlese.Text.Split('_');
            textBoxCamereAlese.Text = "";
            idCameraHotel = idCameraHotelMaxim;
            idGasit = false;
            while (idCameraHotel >= idCameraHotelMinim && !idGasit)
            {
                if (!textBoxSelectieCamereHotel.Text.Contains(idCameraHotel.ToString()))
                {
                    con.Open();
                    cmd.CommandText = "select id from camere where id='" + idCameraHotel.ToString() + "' and id_hotel in (select id from hoteluri where denumire='" + comboBoxHotel.Text + "')";
                    dr = cmd.ExecuteReader();
                    if (dr.HasRows)
                        idGasit = true;
                    con.Close();
                }
                idCameraHotel--;
            }
            idMaximCamera = idCameraHotel + 1;
            idCameraHotel = idCameraHotelMinim;
            idGasit = false;
            while (idCameraHotel <= idCameraHotelMaxim && !idGasit)
            {
                if (!textBoxSelectieCamereHotel.Text.Contains(idCameraHotel.ToString()))
                {
                    con.Open();
                    cmd.CommandText = "select id from camere where id='" + idCameraHotel.ToString() + "' and id_hotel in (select id from hoteluri where denumire='" + comboBoxHotel.Text + "')";
                    dr = cmd.ExecuteReader();
                    if (dr.HasRows)
                        idGasit = true;
                    con.Close();
                }
                idCameraHotel++;
            }
            idMinimCamera = idCameraHotel - 1;
            idCamera = idMinimCamera;
            for (int i = 0; i < NrCamere && idCamera <= idMaximCamera; i++)
            {
                idCamera = idMinimCamera;
                cameraGasita = false;
                while(!cameraGasita)
                {
                    con.Open();
                    cmd.CommandText = "select id from camere where id='" + idCamera.ToString() + "' and id_tip_camera in (select id from tip_camere where tip='" + camereAlese[i] + "') and id_hotel in (select id from hoteluri where denumire='" + comboBoxHotel.Text + "')";
                    dr = cmd.ExecuteReader();
                    if (dr.HasRows)
                        if (!textBoxCamereAlese.Text.Contains(idCamera.ToString()) && !textBoxSelectieCamereHotel.Text.Contains(idCamera.ToString()))
                            while (dr.Read())
                            {
                                textBoxCamereAlese.Text += dr[0].ToString() + "_";
                                cameraGasita = true;
                            }
                    con.Close();
                    idCamera++;
                }
            }
            if (aeroport == 1)
            {
                con.Open();
                cmd.CommandText = "select id from distante where id_loc1=(select id from localitati where localitate='" + comboBoxAeroportClient.Text + "') and id_loc2=(select id from localitati where localitate='" + comboBoxAeroportHotel.Text + "')";
                dr = cmd.ExecuteReader();
                if (dr.HasRows)
                    while (dr.Read())
                        textBoxIdDistanta.Text = dr[0].ToString();
                con.Close();
            }
            else
                textBoxIdDistanta.Text = "0";
            con.Open();
            cmd.CommandText = "insert into rezervari(data, nr_zile, id_camera, id_client, id_tip_transport, id_hotel, tip_bilet, id_distanta) values('" + dateTimePickerDataRezervare.Text + "', '" + textBoxNrZile.Text + "', '" + textBoxCamereAlese.Text + "', (select id from clienti where email='" + textBoxEmail.Text + "'), (select id from tip_transporturi where tip='" + comboBoxTipTransport.Text + "'), (select id from hoteluri where denumire='" + comboBoxHotel.Text + "'), '" + textBoxTipBilet.Text + "', '" + textBoxIdDistanta.Text + "')";
            cmd.ExecuteNonQuery();
            MessageBox.Show("The reservation has been made!", "Succsess!!!", MessageBoxButtons.OK, MessageBoxIcon.None);
            con.Close();
            incarcareDateInceput();
            pozitieButoaneInceput();
            ascundereButoaneInceput();
            stergeInformatii();
        }

        private void comboBoxSosire_SelectedIndexChanged(object sender, EventArgs e)
        {
            comboBoxAeroportHotel.Items.Clear();
            comboBoxAeroportHotel.Text = "";
            textBoxDistantaHotelAeroport.Text = "";
            con.Open();
            cmd.CommandText = "select (select localitate from localitati where id_loc2=id) from distante where id_loc1=(select id from localitati where localitate='" + comboBoxSosire.Text + "') and id_loc2 in (select id_localitate from aeroporturi) and id_loc2 in (select id from localitati where id_regiune in (select id from regiuni where regiune='" + textBoxRegiune.Text + "' and id_tara in (select id from tari where tara='" + textBoxTara.Text + "')))";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxAeroportHotel.Items.Add(dr[0].ToString());
            con.Close();
            con.Open();
            cmd.CommandText = "select min(distanta) from distante where id_loc1=(select id from localitati where localitate='" + comboBoxSosire.Text + "') and id_loc2 in (select id_localitate from aeroporturi)";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    textBoxDistantaHotelAeroport2.Text = dr[0].ToString();
            con.Close();
            con.Open();
            cmd.CommandText = "select (select localitate from localitati where id=id_loc2) from distante where id_loc1=(select id from localitati where localitate='" + comboBoxSosire.Text + "') and distanta='" + textBoxDistantaHotelAeroport2.Text + "' and id_loc2 in (select id_localitate from aeroporturi)";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    textBoxAeroportHotelApropiat.Text = dr[0].ToString();
            con.Close();
            comboBoxAeroportHotel.Text = "Select an airport";
        }

        private void comboBoxAeroportHotelApropiat_SelectedIndexChanged(object sender, EventArgs e)
        {
            textBoxDistantaHotelAeroport.Text = "";
            con.Open();
            cmd.CommandText = "select distanta from distante where id_loc1=(select id from localitati where localitate='" + comboBoxSosire.Text + "') and id_loc2=(select id from localitati where localitate='" + comboBoxAeroportHotel.Text + "')";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    textBoxDistantaHotelAeroport.Text = dr[0].ToString();
            con.Close();
            if (aeroport == 1)
            {
                if (comboBoxTipBilet.Text != "Select a ticket type" && comboBoxAeroportClient.Text != "Select an airport" && comboBoxAeroportHotel.Text != "Select an airport" && textBoxNrZile.Text != "" && comboBoxCameraHotel.Text != "Select a room" && cameraN == NrCamere + 1)
                    buttonRezervare.Show();
                else
                    buttonRezervare.Hide();
            }
            else
            {
                if (textBoxNrZile.Text != "" && comboBoxCameraHotel.Text != "Select a room" && cameraN == NrCamere + 1)
                    buttonRezervare.Show();
                else
                    buttonRezervare.Hide();
            }
            incarcareInformatiiRezervare();
        }

        private void textBoxNrCamere_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsDigit(e.KeyChar) && !char.IsControl(e.KeyChar))
                e.Handled = true;
            else
                e.Handled = false;
        }

        private void textBoxNrCamere_TextChanged(object sender, EventArgs e)
        {
            buttonRezervare.Hide();
            textBoxCamereAlese.Text = "";
            textBoxSelectieCamereHotel.Text = "";
            textBoxCamereDisponibile.Text = "";
            textBoxCapacitate.Text = "";
            cameraN = 1;
            buttonStergeCamera.Hide();
            if (textBoxNrCamere.Text != "" && !textBoxNrCamere.Text.StartsWith("0"))
                NrCamere = int.Parse(textBoxNrCamere.Text);
            else
                NrCamere = 0;
            if(NrCamere != 0)
            {
                idNumaraCamere = 0;
                idCameraHotel = 0;
                con.Open();
                cmd.CommandText = "select max(id) from camere where id_hotel in (select id from hoteluri where denumire='" + comboBoxHotel.Text + "')";
                dr = cmd.ExecuteReader();
                if (dr.HasRows)
                    while (dr.Read())
                        idCameraHotelMaxim = int.Parse(dr[0].ToString());
                con.Close();
                con.Open();
                cmd.CommandText = "select min(id) from camere where id_hotel in (select id from hoteluri where denumire='" + comboBoxHotel.Text + "')";
                dr = cmd.ExecuteReader();
                if (dr.HasRows)
                    while (dr.Read())
                        idCameraHotelMinim = int.Parse(dr[0].ToString());
                con.Close();
                idCameraHotel = idCameraHotelMinim;
                con.Open();
                cmd.CommandText = "select id_camera from rezervari where id_hotel in (select id from hoteluri where denumire='" + comboBoxHotel.Text + "')";
                dr = cmd.ExecuteReader();
                if (dr.HasRows)
                    while (dr.Read())
                        textBoxSelectieCamereHotel.Text += dr[0].ToString();
                con.Close();
                while(idCameraHotel <= idCameraHotelMaxim)
                {
                    if (!textBoxSelectieCamereHotel.Text.Contains(idCameraHotel.ToString()))
                    {
                        con.Open();
                        cmd.CommandText = "select id from camere where id='" + idCameraHotel.ToString() + "' and id_hotel in (select id from hoteluri where denumire='" + comboBoxHotel.Text + "')";
                        dr = cmd.ExecuteReader();
                        if (dr.HasRows)
                            idNumaraCamere++;
                        con.Close();
                    }
                    idCameraHotel++;
                }
                if(idNumaraCamere < NrCamere)
                {
                    MessageBox.Show("There are not enough free rooms for your requirements in this hotel!\nFree rooms left: " + idNumaraCamere.ToString() + "", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                    comboBoxCameraHotel.Enabled = false;
                    comboBoxCameraHotel.Text = "Select a room";
                    comboBoxCameraHotel.BackColor = Color.White;
                    comboBoxCameraHotel.ForeColor = Color.Black;
                    textBoxNrCamere.Text = "";
                }
                else
                {
                    comboBoxCameraHotel.Enabled = true;
                    if(NrCamere > 1)
                        buttonUrmatoareaCamera.Show();
                }
            }
            else
            {
                comboBoxCameraHotel.Enabled = false;
                comboBoxCameraHotel.Text = "Select a room";
                comboBoxCameraHotel.BackColor = Color.White;
                comboBoxCameraHotel.ForeColor = Color.Black;
                textBoxNrCamere.Text = "";
                buttonUrmatoareaCamera.Hide();
            }
            incarcareInformatiiRezervare();
        }

        private void buttonInapoi_Click(object sender, EventArgs e)
        {
            if(intoarcere == 1)
            {
                Romania romania = new Romania(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                romania.ShowDialog();
                this.Close();
            }
            else
            {
                if(intoarcere == 2)
                {
                    Islanda irlanda = new Islanda(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                    irlanda.ShowDialog();
                    this.Close();
                }
                else
                {
                    if(intoarcere == 3)
                    {
                        Norvegia norvegia = new Norvegia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                        norvegia.ShowDialog();
                        this.Close();
                    }
                    else
                    {
                        if(intoarcere == 4)
                        {
                            Suedia suedia = new Suedia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                            suedia.ShowDialog();
                            this.Close();
                        }
                        else
                        {
                            if(intoarcere == 5)
                            {
                                Finlanda finlanda = new Finlanda(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                finlanda.ShowDialog();
                                this.Close();
                            }
                            else
                            {
                                if(intoarcere == 6)
                                {
                                    Estonia estonia = new Estonia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                    estonia.ShowDialog();
                                    this.Close();
                                }
                                else
                                {
                                    if(intoarcere == 7)
                                    {
                                        Rusia rusia = new Rusia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                        rusia.ShowDialog();
                                        this.Close();
                                    }
                                    else
                                    {
                                        if(intoarcere == 8)
                                        {
                                            Letonia letonia = new Letonia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                            letonia.ShowDialog();
                                            this.Close();
                                        }
                                        else
                                        {
                                            if(intoarcere == 9)
                                            {
                                                Lituania lituania = new Lituania(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                lituania.ShowDialog();
                                                this.Close();
                                            }
                                            else
                                            {
                                                if(intoarcere == 10)
                                                {
                                                    Belarus belarus = new Belarus(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                    belarus.ShowDialog();
                                                    this.Close();
                                                }
                                                else
                                                {
                                                    if(intoarcere == 11)
                                                    {
                                                        Ucraina ucraina = new Ucraina(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                        ucraina.ShowDialog();
                                                        this.Close();
                                                    }
                                                    else
                                                    {
                                                        if(intoarcere == 12)
                                                        {
                                                            RepMoldova moldova = new RepMoldova(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                            moldova.ShowDialog();
                                                            this.Close();
                                                        }
                                                        else
                                                        {
                                                            if(intoarcere == 13)
                                                            {
                                                                Bulgaria bulgaria = new Bulgaria(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                bulgaria.ShowDialog();
                                                                this.Close();
                                                            }
                                                            else
                                                            {
                                                                if(intoarcere == 14)
                                                                {
                                                                    Cipru cipru = new Cipru(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                    cipru.ShowDialog();
                                                                    this.Close();
                                                                }
                                                                else
                                                                {
                                                                    if(intoarcere == 15)
                                                                    {
                                                                        Malta malta = new Malta(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                        malta.ShowDialog();
                                                                        this.Close();
                                                                    }
                                                                    else
                                                                    {
                                                                        if(intoarcere == 16)
                                                                        {
                                                                            Grecia grecia = new Grecia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                            grecia.ShowDialog();
                                                                            this.Close();
                                                                        }
                                                                        else
                                                                        {
                                                                            if(intoarcere == 17)
                                                                            {
                                                                                Macedonia macedonia = new Macedonia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                                macedonia.ShowDialog();
                                                                                this.Close();
                                                                            }
                                                                            else
                                                                            {
                                                                                if(intoarcere == 18)
                                                                                {
                                                                                    Albania albania = new Albania(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                                    albania.ShowDialog();
                                                                                    this.Close();
                                                                                }
                                                                                else
                                                                                {
                                                                                    if(intoarcere == 19)
                                                                                    {
                                                                                        Kosovo kosovo = new Kosovo(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                                        kosovo.ShowDialog();
                                                                                        this.Close();
                                                                                    }
                                                                                    else
                                                                                    {
                                                                                        if(intoarcere == 20)
                                                                                        {
                                                                                            Muntenegru muntenegru = new Muntenegru(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                                            muntenegru.ShowDialog();
                                                                                            this.Close();
                                                                                        }
                                                                                        else
                                                                                        {
                                                                                            if(intoarcere == 21)
                                                                                            {
                                                                                                Serbia serbia = new Serbia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                                                serbia.ShowDialog();
                                                                                                this.Close();
                                                                                            }
                                                                                            else
                                                                                            {
                                                                                                if(intoarcere == 22)
                                                                                                {
                                                                                                    BosniaHertegovina bosniaHertegovina = new BosniaHertegovina(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                                                    bosniaHertegovina.ShowDialog();
                                                                                                    this.Close();
                                                                                                }
                                                                                                else
                                                                                                {
                                                                                                    if(intoarcere == 23)
                                                                                                    {
                                                                                                        Croatia croatia = new Croatia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                                                        croatia.ShowDialog();
                                                                                                        this.Close();
                                                                                                    }
                                                                                                    else
                                                                                                    {
                                                                                                        if(intoarcere == 24)
                                                                                                        {
                                                                                                            Ungaria ungaria = new Ungaria(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                                                            ungaria.ShowDialog();
                                                                                                            this.Close();
                                                                                                        }
                                                                                                        else
                                                                                                        {
                                                                                                            if (intoarcere == 25)
                                                                                                            {
                                                                                                                Slovenia slovenia = new Slovenia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                                                                slovenia.ShowDialog();
                                                                                                                this.Close();
                                                                                                            }
                                                                                                            else
                                                                                                            {
                                                                                                                if(intoarcere == 26)
                                                                                                                {
                                                                                                                    Slovacia slovacia = new Slovacia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                                                                    slovacia.ShowDialog();
                                                                                                                    this.Close();
                                                                                                                }
                                                                                                                else
                                                                                                                {
                                                                                                                    if(intoarcere == 27)
                                                                                                                    {
                                                                                                                        Austria austria = new Austria(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                                                                        austria.ShowDialog();
                                                                                                                        this.Close();
                                                                                                                    }
                                                                                                                    else
                                                                                                                    {
                                                                                                                        if(intoarcere == 28)
                                                                                                                        {
                                                                                                                            RepCeha repCeha = new RepCeha(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                                                                            repCeha.ShowDialog();
                                                                                                                            this.Close();
                                                                                                                        }
                                                                                                                        else
                                                                                                                        {
                                                                                                                            if(intoarcere == 29)
                                                                                                                            {
                                                                                                                                Polonia polonia = new Polonia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                                                                                polonia.ShowDialog();
                                                                                                                                this.Close();
                                                                                                                            }
                                                                                                                            else
                                                                                                                            {
                                                                                                                                if(intoarcere == 30)
                                                                                                                                {
                                                                                                                                    Danemarca danemarca = new Danemarca(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                                                                                    danemarca.ShowDialog();
                                                                                                                                    this.Close();
                                                                                                                                }
                                                                                                                                else
                                                                                                                                {
                                                                                                                                    if(intoarcere == 31)
                                                                                                                                    {
                                                                                                                                        Germania germania = new Germania(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                                                                                        germania.ShowDialog();
                                                                                                                                        this.Close();
                                                                                                                                    }
                                                                                                                                    else
                                                                                                                                    {
                                                                                                                                        if(intoarcere == 32)
                                                                                                                                        {
                                                                                                                                            Olanda olanda = new Olanda(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                                                                                            olanda.ShowDialog();
                                                                                                                                            this.Close();
                                                                                                                                        }
                                                                                                                                        else
                                                                                                                                        {
                                                                                                                                            if(intoarcere == 33)
                                                                                                                                            {
                                                                                                                                                Belgia belgia = new Belgia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                                                                                                belgia.ShowDialog();
                                                                                                                                                this.Close();
                                                                                                                                            }
                                                                                                                                            else
                                                                                                                                            {
                                                                                                                                                if(intoarcere == 34)
                                                                                                                                                {
                                                                                                                                                    Luxemburg luxemburg = new Luxemburg(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                                                                                                    luxemburg.ShowDialog();
                                                                                                                                                    this.Close();
                                                                                                                                                }
                                                                                                                                                else
                                                                                                                                                {
                                                                                                                                                    if(intoarcere == 35)
                                                                                                                                                    {
                                                                                                                                                        Liechtenstein liechtenstein = new Liechtenstein(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                                                                                                        liechtenstein.ShowDialog();
                                                                                                                                                        this.Close();
                                                                                                                                                    }
                                                                                                                                                    else
                                                                                                                                                    {
                                                                                                                                                        if(intoarcere == 36)
                                                                                                                                                        {
                                                                                                                                                            Elvetia elvetia = new Elvetia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                                                                                                            elvetia.ShowDialog();
                                                                                                                                                            this.Close();
                                                                                                                                                        }
                                                                                                                                                        else
                                                                                                                                                        {
                                                                                                                                                            if(intoarcere == 37)
                                                                                                                                                            {
                                                                                                                                                                Italia italia = new Italia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                                                                                                                italia.ShowDialog();
                                                                                                                                                                this.Close();
                                                                                                                                                            }
                                                                                                                                                            else
                                                                                                                                                            {
                                                                                                                                                                if(intoarcere == 38)
                                                                                                                                                                {
                                                                                                                                                                    SanMarino sanMarino = new SanMarino(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                                                                                                                    sanMarino.ShowDialog();
                                                                                                                                                                    this.Close();
                                                                                                                                                                }
                                                                                                                                                                else
                                                                                                                                                                {
                                                                                                                                                                    if(intoarcere == 39)
                                                                                                                                                                    {
                                                                                                                                                                        Vatican vatican = new Vatican(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                                                                                                                        vatican.ShowDialog();
                                                                                                                                                                        this.Close();
                                                                                                                                                                    }
                                                                                                                                                                    else
                                                                                                                                                                    {
                                                                                                                                                                        if(intoarcere == 40)
                                                                                                                                                                        {
                                                                                                                                                                            Monaco monaco = new Monaco(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                                                                                                                            monaco.ShowDialog();
                                                                                                                                                                            this.Close();
                                                                                                                                                                        }
                                                                                                                                                                        else
                                                                                                                                                                        {
                                                                                                                                                                            if(intoarcere == 41)
                                                                                                                                                                            {
                                                                                                                                                                                Franta franta = new Franta(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                                                                                                                                franta.ShowDialog();
                                                                                                                                                                                this.Close();
                                                                                                                                                                            }
                                                                                                                                                                            else
                                                                                                                                                                            {
                                                                                                                                                                                if(intoarcere == 42)
                                                                                                                                                                                {
                                                                                                                                                                                    Andorra andorra = new Andorra(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                                                                                                                                    andorra.ShowDialog();
                                                                                                                                                                                    this.Close();
                                                                                                                                                                                }
                                                                                                                                                                                else
                                                                                                                                                                                {
                                                                                                                                                                                    if(intoarcere == 43)
                                                                                                                                                                                    {
                                                                                                                                                                                        Spania spania = new Spania(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                                                                                                                                        spania.ShowDialog();
                                                                                                                                                                                        this.Close();
                                                                                                                                                                                    }
                                                                                                                                                                                    else
                                                                                                                                                                                    {
                                                                                                                                                                                        if(intoarcere == 44)
                                                                                                                                                                                        {
                                                                                                                                                                                            Portugalia portugalia = new Portugalia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                                                                                                                                            portugalia.ShowDialog();
                                                                                                                                                                                            this.Close();
                                                                                                                                                                                        }
                                                                                                                                                                                        else
                                                                                                                                                                                        {
                                                                                                                                                                                            if(intoarcere == 45)
                                                                                                                                                                                            {
                                                                                                                                                                                                Irlanda irlanda = new Irlanda(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                                                                                                                                                irlanda.ShowDialog();
                                                                                                                                                                                                this.Close();
                                                                                                                                                                                            }
                                                                                                                                                                                            else
                                                                                                                                                                                            {
                                                                                                                                                                                                if(intoarcere == 46)
                                                                                                                                                                                                {
                                                                                                                                                                                                    RegatulUnit regatulUnit = new RegatulUnit(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
                                                                                                                                                                                                    regatulUnit.ShowDialog();
                                                                                                                                                                                                    this.Close();
                                                                                                                                                                                                }
                                                                                                                                                                                            }
                                                                                                                                                                                        }
                                                                                                                                                                                    }
                                                                                                                                                                                }
                                                                                                                                                                            }
                                                                                                                                                                        }
                                                                                                                                                                    }
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                        }
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
