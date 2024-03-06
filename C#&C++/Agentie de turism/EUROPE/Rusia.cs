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
    public partial class Rusia : Form
    {
        SqlConnection con = new SqlConnection(@"Data Source=(LocalDB)\MSSQLLocalDB;AttachDbFilename=D:\CSharp\Agentie de turism\Agentie de turism\Database1.mdf;Integrated Security=True");
        SqlCommand cmd = new SqlCommand();
        SqlDataReader dr;

        int hotel = 1;

        void ascundeCasute()
        {
            textBoxCont.Hide();
            textBoxContinent.Hide();
            textBoxOras.Hide();
            textBoxTara.Hide();
            textBoxRegiune.Hide();
        }

        void pozitieButoane()
        {
            comboBoxVest.Location = new Point(comboBoxVest.Location.X, comboBoxVest.Location.Y + 150);
            comboBoxVest.Size = new Size(150, 24);
        }

        void incarcareDate()
        {
            comboBoxVest.Items.Clear();
            textBoxRegiune.Text = "West";
            con.Open();
            cmd.CommandText = "select distinct (select localitate from localitati where id=id_localitate) from hoteluri where id_regiune in (select id from regiuni where regiune='" + textBoxRegiune.Text + "' and id_tara=(select id from tari where tara='" + textBoxTara.Text + "'))";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxVest.Items.Add(dr[0].ToString());
            con.Close();
            comboBoxVest.Text = "Select a city";
        }

        public Rusia(string Cont, string Continent, string Tara)
        {
            InitializeComponent();
            textBoxCont.Text = Cont.ToString();
            textBoxContinent.Text = Continent.ToString();
            textBoxTara.Text = Tara.ToString();
        }

        private void Rusia_Load(object sender, EventArgs e)
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
            panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Kazan__HOTEL_COURTYARD;
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

        private void comboBoxVest_KeyPress(object sender, KeyPressEventArgs e)
        {
            e.Handled = true;
        }

        private void buttonInformatii_Click(object sender, EventArgs e)
        {
            hotel = 1;
            panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Kazan__HOTEL_COURTYARD;
            panelHoteluri.Hide();
            panelTara.Hide();
            panelInformatii.Show();
        }

        private void buttonTara_Click(object sender, EventArgs e)
        {
            hotel = 1;
            panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Kazan__HOTEL_COURTYARD;
            panelTara.Show();
            panelInformatii.Hide();
            panelHoteluri.Hide();
        }

        private void buttonHotelUrmator_Click(object sender, EventArgs e)
        {
            hotel++;
            if (hotel <= 6)
            {
                if (hotel == 1)
                    panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Kazan__HOTEL_COURTYARD;
                else
                    if (hotel == 2)
                        panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Moscova__HOTEL_BELGRAD_HOTEL;
                    else
                        if (hotel == 3)
                            panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Nijni__HOTEL_PARK_INN;
                        else
                            if (hotel == 4)
                                panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Pskov__HOTEL_PUSHKIN;
                            else
                                if (hotel == 5)
                                    panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Samara__HOTEL_LOTTE;
                                else
                                    if (hotel == 6)
                                        panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Voronej__HOTEL_MARRIOTT;
            }
            else
            {
                hotel = 1;
                panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Kazan__HOTEL_COURTYARD;
            }
        }

        private void buttonHotelAnterior_Click(object sender, EventArgs e)
        {
            hotel--;
            if (hotel >= 1)
            {
                if (hotel == 1)
                    panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Kazan__HOTEL_COURTYARD;
                else
                    if (hotel == 2)
                        panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Moscova__HOTEL_BELGRAD_HOTEL;
                    else
                        if (hotel == 3)
                            panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Nijni__HOTEL_PARK_INN;
                        else
                            if (hotel == 4)
                                panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Pskov__HOTEL_PUSHKIN;
                            else
                                if (hotel == 5)
                                    panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Samara__HOTEL_LOTTE;
                                else
                                    if (hotel == 6)
                                        panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Voronej__HOTEL_MARRIOTT;
            }
            else
            {
                hotel = 6;
                panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Voronej__HOTEL_MARRIOTT;
            }
        }

        private void buttonHoteluri_Click(object sender, EventArgs e)
        {
            panelHoteluri.Show();
            panelInformatii.Hide();
            panelTara.Hide();
        }

        private void comboBoxVest_SelectedIndexChanged(object sender, EventArgs e)
        {
            textBoxOras.Text = comboBoxVest.Text;
            Rezervare rez = new Rezervare(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text, textBoxOras.Text, textBoxRegiune.Text);
            rez.ShowDialog();
            this.Close();
        }
    }
}
