﻿using System;
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
    public partial class Suedia : Form
    {

        SqlConnection con = new SqlConnection(@"Data Source=(LocalDB)\MSSQLLocalDB;AttachDbFilename=D:\CSharp\Agentie de turism\Agentie de turism\Database1.mdf;Integrated Security=True");
        SqlCommand cmd = new SqlCommand();
        SqlDataReader dr;

        int kN = 0, kS = 0, hotel = 1;

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
            buttonNord.Location = new Point(buttonNord.Location.X + 500, buttonNord.Location.Y );
            buttonSud.Location = new Point(buttonSud.Location.X - 400, buttonSud.Location.Y - 50);

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

        public Suedia(string Cont, string Continent, string Tara)
        {
            InitializeComponent();
            textBoxCont.Text = Cont.ToString();
            textBoxContinent.Text = Continent.ToString();
            textBoxTara.Text = Tara.ToString();
        }

        private void Suedia_Load(object sender, EventArgs e)
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
            panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Göteborg__HOTEL_FIRST_G;
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
            panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Göteborg__HOTEL_FIRST_G;
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
            panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Göteborg__HOTEL_FIRST_G;
            panelTara.Show();
            panelInformatii.Hide();
            panelHoteluri.Hide();
        }

        private void buttonHotelUrmator_Click(object sender, EventArgs e)
        {
            hotel++;
            if (hotel <= 4)
            {
                if (hotel == 1)
                    panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Göteborg__HOTEL_FIRST_G;
                else
                    if (hotel == 2)
                        panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Jönköping__HOTEL_SCANDIC;
                    else
                        if (hotel == 3)
                            panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Skellefteå__HOTEL_SCANDIC;
                        else
                            if (hotel == 4)
                                panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Stockholm__HOTEL_DIPLOMAT;
            }
            else
            {
                hotel = 1;
                panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Göteborg__HOTEL_FIRST_G;
            }
        }

        private void buttonHotelAnterior_Click(object sender, EventArgs e)
        {
            hotel--;
            if (hotel >= 1)
            {
                if (hotel == 1)
                    panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Göteborg__HOTEL_FIRST_G;
                else
                    if (hotel == 2)
                        panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Jönköping__HOTEL_SCANDIC;
                    else
                        if (hotel == 3)
                            panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Skellefteå__HOTEL_SCANDIC;
                        else
                            if (hotel == 4)
                                panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Stockholm__HOTEL_DIPLOMAT;
            }
            else
            {
                hotel = 4;
                panelHoteluri.BackgroundImage = Agentie_de_turism.Properties.Resources.Stockholm__HOTEL_DIPLOMAT;
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
