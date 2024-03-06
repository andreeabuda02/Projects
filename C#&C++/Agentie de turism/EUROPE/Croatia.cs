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
    public partial class Croatia : Form
    {
        SqlConnection con = new SqlConnection(@"Data Source=(LocalDB)\MSSQLLocalDB;AttachDbFilename=D:\CSharp\Agentie de turism\Agentie de turism\Database1.mdf;Integrated Security=True");
        SqlCommand cmd = new SqlCommand();
        SqlDataReader dr;

        int kN = 0, kS = 0, hotel = 0;

        void ascundeCasute()
        {
            textBoxCont.Hide();
            textBoxContinent.Hide();
            textBoxOras.Hide();
            textBoxTara.Hide();
            textBoxRegiune.Hide();
            comboBoxNord.Hide();
            comboBoxSud.Hide();
        }

        void pozitieButoane()
        {
            buttonNord.Location = new Point(buttonNord.Location.X, buttonNord.Location.Y + 50);
            buttonSud.Location = new Point(buttonSud.Location.X, buttonSud.Location.Y - 150);

            comboBoxNord.Location = new Point(buttonNord.Location.X + 104, buttonNord.Location.Y + 16);
            comboBoxSud.Location = new Point(buttonSud.Location.X + 104, buttonSud.Location.Y + 16);

            comboBoxNord.Size = new Size(150, 24);
            comboBoxSud.Size = new Size(150, 24);
        }

        void incarcareDate()
        {
            comboBoxNord.Items.Clear();
            comboBoxSud.Items.Clear();
            textBoxRegiune.Text = "North";
            con.Open();
            cmd.CommandText = "select distinct (select localitate from localitati where id=id_localitate) from hoteluri where id_regiune in (select id from regiuni where regiune='" + textBoxRegiune.Text + "' and id_tara=(select id from tari where tara='" + textBoxTara.Text + "'))";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxNord.Items.Add(dr[0].ToString());
            con.Close();
            textBoxRegiune.Text = "South";
            con.Open();
            cmd.CommandText = "select distinct (select localitate from localitati where id=id_localitate) from hoteluri where id_regiune in (select id from regiuni where regiune='" + textBoxRegiune.Text + "' and id_tara=(select id from tari where tara='" + textBoxTara.Text + "'))";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxSud.Items.Add(dr[0].ToString());
            con.Close();
            textBoxRegiune.Text = "";
            comboBoxNord.Text = "Select a city";
            comboBoxSud.Text = "Select a city";
        }

        public Croatia(string Cont, string Continent, string Tara)
        {
            InitializeComponent();
            textBoxCont.Text = Cont.ToString();
            textBoxContinent.Text = Continent.ToString();
            textBoxTara.Text = Tara.ToString();
        }

        private void Croatia_Load(object sender, EventArgs e)
        {
            cmd.Connection = con;
            FormBorderStyle = FormBorderStyle.None;
            WindowState = FormWindowState.Maximized;
            panelTara.Dock = DockStyle.Fill;
            panelInformatii.Dock = DockStyle.Fill;
            panelHoteluri.Dock = DockStyle.Fill;
            panelInformatii.Show();
            panelInformatii.Hide();
            panelHoteluri.Hide();
            ascundeCasute();
            pozitieButoane();
            incarcareDate();
            panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Dokovo__HOTEL_DAKOVO;
        }

        private void buttonInapoi_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 1;
            Agentie_de_turism.Properties.Settings.Default.Tara = 0;
            Europa eu = new Europa(textBoxCont.Text, textBoxContinent.Text);
            Agentie_de_turism.Properties.Settings.Default.Tara = 0;
            eu.ShowDialog();
            this.Close();
        }

        private void buttonNord_Click(object sender, EventArgs e)
        {
            kN++;
            textBoxRegiune.Text = "North";
            if (kN % 2 == 1)
                comboBoxNord.Show();
            else
            {
                comboBoxNord.Hide();
                comboBoxNord.Text = "Select a city";
                kN = 0;
            }
        }

        private void buttonSud_Click(object sender, EventArgs e)
        {
            kS++;
            textBoxRegiune.Text = "South";
            if (kS % 2 == 1)
                comboBoxSud.Show();
            else
            {
                comboBoxSud.Hide();
                comboBoxSud.Text = "Select a city";
                kS = 0;
            }
        }

        private void comboBoxNord_KeyPress(object sender, KeyPressEventArgs e)
        {
            e.Handled = true;
        }

        private void comboBoxSud_KeyPress(object sender, KeyPressEventArgs e)
        {
            e.Handled = true;
        }

        private void comboBoxNord_SelectedIndexChanged(object sender, EventArgs e)
        {
            textBoxOras.Text = comboBoxNord.Text;
            Rezervare rez = new Rezervare(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text, textBoxOras.Text, textBoxRegiune.Text);
            rez.ShowDialog();
            this.Close();
        }

        private void buttonInformatii_Click(object sender, EventArgs e)
        {
            hotel = 1;
            panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Dokovo__HOTEL_DAKOVO;
            comboBoxNord.Hide();
            comboBoxSud.Hide();
            kN = kS = 0;
            panelHoteluri.Hide();
            panelTara.Hide();
            panelInformatii.Show();
        }

        private void buttonTara_Click(object sender, EventArgs e)
        {
            hotel = 1;
            panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Dokovo__HOTEL_DAKOVO;
            panelTara.Show();
            panelInformatii.Hide();
            panelHoteluri.Hide();
        }

        private void buttonHotelUrmator_Click(object sender, EventArgs e)
        {
            hotel++;
            if (hotel <= 11)
            {
                if (hotel == 1)
                    panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Dokovo__HOTEL_DAKOVO;
                else
                    if (hotel == 2)
                        panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Dubrovnik__HOTEL_DUBROVNIK_PALACE;
                    else
                        if (hotel == 3)
                            panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Karlovak__HOTEL_EUROPA;
                        else
                            if (hotel == 4)
                                panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Makarska__HOTEL_METEOR;
                            else
                                if (hotel == 5)
                                    panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Omiš__HOTEL_HILDEGARD;
                                else
                                    if (hotel == 6)
                                        panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Rijeka__HOTEL_JADRAN;
                                    else
                                        if (hotel == 7)
                                            panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Rovinj__HOTEL_GRAND_PARK;
                                        else
                                            if (hotel == 8)
                                                panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Šibenik__HOTEL_NIKO;
                                            else
                                                if (hotel == 9)
                                                    panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Split__HOTEL_SPLIT;
                                                else
                                                    if (hotel == 10)
                                                        panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Zadar__HOTEL_ZADAR;
                                                    else
                                                        if (hotel == 11)
                                                            panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Zagreb__HOTEL_ASTORIA;
            }
            else
            {
                hotel = 1;
                panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Dokovo__HOTEL_DAKOVO;
            }
        }

        private void buttonHotelAnterior_Click(object sender, EventArgs e)
        {
            hotel--;
            if (hotel >= 1)
            {
                if (hotel == 1)
                    panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Dokovo__HOTEL_DAKOVO;
                else
                    if (hotel == 2)
                        panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Dubrovnik__HOTEL_DUBROVNIK_PALACE;
                    else
                        if (hotel == 3)
                            panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Karlovak__HOTEL_EUROPA;
                        else
                            if (hotel == 4)
                                panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Makarska__HOTEL_METEOR;
                            else
                                if (hotel == 5)
                                    panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Omiš__HOTEL_HILDEGARD;
                                else
                                    if (hotel == 6)
                                        panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Rijeka__HOTEL_JADRAN;
                                    else
                                        if (hotel == 7)
                                            panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Rovinj__HOTEL_GRAND_PARK;
                                        else
                                            if (hotel == 8)
                                                panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Šibenik__HOTEL_NIKO;
                                            else
                                                if (hotel == 9)
                                                    panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Split__HOTEL_SPLIT;
                                                else
                                                    if (hotel == 10)
                                                        panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Zadar__HOTEL_ZADAR;
                                                    else
                                                        if (hotel == 11)
                                                            panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Zagreb__HOTEL_ASTORIA;
            }
            else
            {
                hotel = 11;
                panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Zagreb__HOTEL_ASTORIA;
            }
        }

        private void buttonHoteluri_Click(object sender, EventArgs e)
        {
            panelHoteluri.Show();
            panelInformatii.Hide();
            panelTara.Hide();
        }

        private void comboBoxSud_SelectedIndexChanged(object sender, EventArgs e)
        {
            textBoxOras.Text = comboBoxSud.Text;
            Rezervare rez = new Rezervare(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text, textBoxOras.Text, textBoxRegiune.Text);
            rez.ShowDialog();
            this.Close();
        }
    }
}
