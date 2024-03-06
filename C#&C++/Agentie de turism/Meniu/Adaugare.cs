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
using System.Text.RegularExpressions;

namespace Agentie_de_turism
{
    public partial class Adaugare : Form
    {
        SqlConnection con = new SqlConnection(@"Data Source=(LocalDB)\MSSQLLocalDB;AttachDbFilename=D:\CSharp\Agentie de turism\Agentie de turism\Database1.mdf;Integrated Security=True");
        SqlCommand cmd = new SqlCommand();
        SqlDataReader dr;

        public Adaugare()
        {
            InitializeComponent();
        }

        private void Adaugare_Load(object sender, EventArgs e)
        {
            cmd.Connection = con;
            this.Size = new Size(276, 480);
            labelInfo.Show();
            panelAdaugaAeroport.Hide();
            panelStergeAeroport.Hide();
            panelContinent.Hide();
            panelLocalitate.Hide();
            panelTara.Hide();
            panelRegiune.Hide();
            panelCamera.Hide();
            panelTipCamera.Hide();
            panelTipTransport.Hide();
            panelDistante.Hide();
            panelHotel.Hide();
            panelDelCamera.Hide();
            panelDelContinent.Hide();
            panelDelDistanta.Hide();
            panelDelHotel.Hide();
            panelDelLocalitate.Hide();
            panelDelRegiune.Hide();
            panelDelTara.Hide();
            panelDelTipCamera.Hide();
            panelDelTipTransport.Hide();
            panelZona.Hide();
            panelDelZona.Hide();
            panelAdaugaAeroport.Location = new Point(12, 24);
            panelStergeAeroport.Location = new Point(12, 24);
            panelContinent.Location = new Point(12, 24);
            panelTara.Location = new Point(12, 24);
            panelRegiune.Location = new Point(12, 24);
            panelLocalitate.Location = new Point(12, 24);
            panelCamera.Location = new Point(12, 24);
            panelTipTransport.Location = new Point(12, 24);
            panelDistante.Location = new Point(12, 24);
            panelHotel.Location = new Point(12, 24);
            panelTipCamera.Location = new Point(12, 24);
            panelZona.Location = new Point(12, 24);
            panelDelCamera.Location = new Point(12, 24);
            panelDelContinent.Location = new Point(12, 24);
            panelDelDistanta.Location = new Point(12, 24);
            panelDelHotel.Location = new Point(12, 24);
            panelDelLocalitate.Location = new Point(12, 24);
            panelDelRegiune.Location = new Point(12, 24);
            panelDelTara.Location = new Point(12, 24);
            panelDelTipCamera.Location = new Point(12, 24);
            panelDelTipTransport.Location = new Point(12, 24);
            panelDelZona.Location = new Point(12, 24);
            buttonFinalizare.Location = new Point(54, 392);
            textBoxLoc1D.Hide();
            textBoxLoc2D.Hide();
            textBoxHotelD.Hide();
            textBoxDistantaZero.Hide();
            textBoxStergeAeroport.Hide();
            textBoxTaraDinCombo.Hide();
            textBoxDelDistanta.BackColor = Color.White;
            textBoxHotelD.BackColor = Color.White;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            panelContinent.Hide();
            panelLocalitate.Hide();
            panelTara.Hide();
            panelRegiune.Hide();
            panelCamera.Hide();
            panelTipCamera.Hide();
            panelTipTransport.Hide();
            panelDistante.Hide();
            panelHotel.Hide();
            panelDelCamera.Hide();
            panelDelContinent.Hide();
            panelDelDistanta.Hide();
            panelDelHotel.Hide();
            panelDelLocalitate.Hide();
            panelDelRegiune.Hide();
            panelDelTara.Hide();
            panelDelTipCamera.Hide();
            panelDelTipTransport.Hide();
            panelZona.Hide();
            panelDelZona.Hide();
            this.Hide();
        }

        void incarcareContinente()
        {
            comboBoxContinent.Items.Clear();
            comboBoxDelContinent.Items.Clear();
            con.Open();
            cmd.CommandText = "select continent from continente";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                {
                    comboBoxContinent.Items.Add(dr[0].ToString());
                    comboBoxDelContinent.Items.Add(dr[0].ToString());
                }
            con.Close();
        }

        void incarcareContinente1()
        {
            comboBoxContinentHotel.Items.Clear();
            con.Open();
            cmd.CommandText = "select continent from continente";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxContinentHotel.Items.Add(dr[0].ToString());
            con.Close();
        }

        void incarcareTari()
        {
            comboBoxTara.Items.Clear();
            comboBoxDelTara.Items.Clear();
            comboBoxTaraLocalitate.Items.Clear();
            comboBoxTaraDelRegiune.Items.Clear();
            con.Open();
            cmd.CommandText = "select tara from tari";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                {
                    comboBoxTara.Items.Add(dr[0].ToString());
                    comboBoxDelTara.Items.Add(dr[0].ToString());
                    comboBoxTaraLocalitate.Items.Add(dr[0].ToString());
                    comboBoxTaraDelRegiune.Items.Add(dr[0].ToString());
                }
            con.Close();
        }

        void incarcareTari1()
        {
            comboBoxTaraHotel.Items.Clear();

            con.Open();
            cmd.CommandText = "select tara from tari";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxTaraHotel.Items.Add(dr[0].ToString());
            con.Close();
        }

        void incarcareRegiuni()
        {
            comboBoxRegiune.Items.Clear();
            comboBoxDelRegiune.Items.Clear();
            con.Open();
            cmd.CommandText = "select distinct regiune from regiuni where id_tara=(select id from tari where tara='" + textBoxTaraDinCombo.Text + "')";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                {
                    comboBoxRegiune.Items.Add(dr[0].ToString());
                    comboBoxDelRegiune.Items.Add(dr[0].ToString());
                }
            con.Close();
        }

        void incarcareRegiuni1()
        {
            comboBoxRegiuneHotel.Items.Clear();
            con.Open();
            cmd.CommandText = "select regiune from regiuni";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxRegiuneHotel.Items.Add(dr[0].ToString());
            con.Close();
        }

        void incarcareLocalitatiAeroport()
        {
            comboBoxAdaugaAeroport.Items.Clear();
            con.Open();
            cmd.CommandText = "select localitate from localitati where id not in (select id_localitate from aeroporturi)";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxAdaugaAeroport.Items.Add(dr[0].ToString());
            con.Close();
        }

        void incarcareLocalitati1()
        {
            comboBoxLoc1.Items.Clear();
            comboBoxDelLocalitate.Items.Clear();
            comboBoxLocalitateZona.Items.Clear();
            comboBoxAdaugaAeroport.Items.Clear();
            con.Open();
            cmd.CommandText = "select localitate from localitati";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                {
                    comboBoxLoc1.Items.Add(dr[0].ToString());
                    comboBoxDelLocalitate.Items.Add(dr[0].ToString());
                    comboBoxLocalitateZona.Items.Add(dr[0].ToString());
                    comboBoxAdaugaAeroport.Items.Add(dr[0].ToString());
                }
            con.Close();
        }

        void incarcareLocalitati2()
        {
            comboBoxLoc2.Items.Clear();
            con.Open();
            cmd.CommandText = "select localitate from localitati where id not in (select id_loc2 from distante where id_loc1=(select id from localitati where localitate='" + comboBoxLoc1.Text + "'))";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxLoc2.Items.Add(dr[0].ToString());
            con.Close();
        }

        void incarcareLocalitati3()
        {
            comboBoxLocalitateHotel.Items.Clear();
            con.Open();
            cmd.CommandText = "select localitate from localitati";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxLocalitateHotel.Items.Add(dr[0].ToString());
            con.Close();
        }

        void incarcareTipCamera()
        {
            comboBoxTipCamera.Items.Clear();
            comboBoxDelTipCamera.Items.Clear();
            con.Open();
            cmd.CommandText = "select tip from tip_camere";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                {
                    comboBoxTipCamera.Items.Add(dr[0].ToString());
                    comboBoxDelTipCamera.Items.Add(dr[0].ToString());
                }
            con.Close();
        }

        void incarcareHotel()
        {
            comboBoxHotel.Items.Clear();
            comboBoxDelHotel.Items.Clear();
            con.Open();
            cmd.CommandText = "select denumire from hoteluri";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                {
                    comboBoxHotel.Items.Add(dr[0].ToString());
                    comboBoxDelHotel.Items.Add(dr[0].ToString());
                    comboBoxHotelD.Items.Add(dr[0].ToString());
                }
            con.Close();
        }

        void incarcareCamera()
        {
            comboBoxDelCamera.Items.Clear();
            con.Open();
            cmd.CommandText = "select nr_camera from camere where id_hotel=(select id from hoteluri where denumire='" + comboBoxHotelD.Text + "')";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxDelCamera.Items.Add(dr[0].ToString());
            con.Close();
        }

        void incarcareTipTransport()
        {
            comboBoxDelTipTransport.Items.Clear();
            con.Open();
            cmd.CommandText = "select tip,pret_km from tip_transporturi";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxDelTipTransport.Items.Add(dr[0].ToString());
            con.Close();
        }

        void incarcareDistanta()
        {
            comboBoxDelLoc1.Items.Clear();
            con.Open();
            cmd.CommandText = "select localitate from localitati";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxDelLoc1.Items.Add(dr[0].ToString());
            con.Close();
        }

        void incarcareDistanta2()
        {
            comboBoxDelLoc2.Items.Clear();
            con.Open();
            cmd.CommandText = "select distinct (select localitate from localitati where id_loc2=id) from distante where id_loc1=(select id from localitati where localitate='" + comboBoxDelLoc1.Text + "')";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxDelLoc2.Items.Add(dr[0].ToString());
            con.Close();
        }

        private void continentToolStripMenuItem_Click(object sender, EventArgs e)
        {
            labelInfo.Hide();
            panelZona.Hide();
            panelDelZona.Hide();
            panelLocalitate.Hide();
            panelTara.Hide();
            panelRegiune.Hide();
            panelCamera.Hide();
            panelTipCamera.Hide();
            panelTipTransport.Hide();
            panelDistante.Hide();
            panelHotel.Hide();
            panelDelCamera.Hide();
            panelDelContinent.Hide();
            panelDelDistanta.Hide();
            panelDelHotel.Hide();
            panelDelLocalitate.Hide();
            panelDelRegiune.Hide();
            panelDelTara.Hide();
            panelDelTipCamera.Hide();
            panelDelTipTransport.Hide();
            panelAdaugaAeroport.Hide();
            panelStergeAeroport.Hide();
            panelContinent.Show();
        }

        private void taraToolStripMenuItem_Click(object sender, EventArgs e)
        {
            labelInfo.Hide();
            panelAdaugaAeroport.Hide();
            panelStergeAeroport.Hide();
            panelZona.Hide();
            panelDelZona.Hide();
            panelContinent.Hide();
            panelLocalitate.Hide();
            panelRegiune.Hide();
            panelCamera.Hide();
            panelTipCamera.Hide();
            panelTipTransport.Hide();
            panelDistante.Hide();
            panelHotel.Hide();
            panelTara.Show();
            panelDelCamera.Hide();
            panelDelContinent.Hide();
            panelDelDistanta.Hide();
            panelDelHotel.Hide();
            panelDelLocalitate.Hide();
            panelDelRegiune.Hide();
            panelDelTara.Hide();
            panelDelTipCamera.Hide();
            panelDelTipTransport.Hide();
            incarcareContinente();
        }

        private void regionToolStripMenuItem_Click(object sender, EventArgs e)
        {
            labelInfo.Hide();
            panelAdaugaAeroport.Hide();
            panelStergeAeroport.Hide();
            panelZona.Hide();
            panelDelZona.Hide();
            panelContinent.Hide();
            panelTara.Hide();
            panelLocalitate.Hide();
            panelCamera.Hide();
            panelTipCamera.Hide();
            panelTipTransport.Hide();
            panelDistante.Hide();
            panelHotel.Hide();
            panelDelCamera.Hide();
            panelDelContinent.Hide();
            panelDelDistanta.Hide();
            panelDelHotel.Hide();
            panelDelLocalitate.Hide();
            panelDelRegiune.Hide();
            panelDelTara.Hide();
            panelDelTipCamera.Hide();
            panelDelTipTransport.Hide();
            panelRegiune.Show();
            incarcareTari();
        }

        private void localityToolStripMenuItem_Click(object sender, EventArgs e)
        {
            labelInfo.Hide();
            panelAdaugaAeroport.Hide();
            panelStergeAeroport.Hide();
            panelZona.Hide();
            panelDelZona.Hide();
            panelContinent.Hide();
            panelTara.Hide();
            panelRegiune.Hide();
            panelCamera.Hide();
            panelTipCamera.Hide();
            panelTipTransport.Hide();
            panelDistante.Hide();
            panelHotel.Hide();
            panelDelCamera.Hide();
            panelDelContinent.Hide();
            panelDelDistanta.Hide();
            panelDelHotel.Hide();
            panelDelLocalitate.Hide();
            panelDelRegiune.Hide();
            panelDelTara.Hide();
            panelDelTipCamera.Hide();
            panelDelTipTransport.Hide();
            panelLocalitate.Show();
            incarcareTari();
        }

        int verificareCon()
        {
            int a=0;
            con.Open();
            cmd.CommandText = "select continent from continente where continent='" + textBoxContinent.Text + "'";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                a=1;
            con.Close();
            return a;
        }

        private void buttonAdaugaCon_Click(object sender, EventArgs e)
        {
            if (textBoxContinent.Text != "")
            {
                
                if (verificareCon()==0)
                {
                    con.Open();
                    cmd.CommandText = "insert into continente (continent) values ('" + textBoxContinent.Text + "')";
                    cmd.ExecuteNonQuery();
                    MessageBox.Show("The continent has been added!", "Success!!!",MessageBoxButtons.OK,MessageBoxIcon.None);
                    con.Close();
                    textBoxContinent.Clear();
                }
                else
                {
                    MessageBox.Show("The continent already exists!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                    textBoxContinent.Clear();
                }
            }
            else
                MessageBox.Show("Insert a continent!", "Warning!!!", MessageBoxButtons.OK,MessageBoxIcon.Exclamation);
        }

        int verificareTa()
        {
            int a = 0;
            con.Open();
            cmd.CommandText = "select tara from tari where tara='" + textBoxTara.Text + "'";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                a = 1;
            con.Close();
            return a;
        }

        private void buttonAdaugaTa_Click(object sender, EventArgs e)
        {
            if(textBoxTara.Text!="" && comboBoxContinent.Text != "")
            {
                if(verificareTa()==0)
                {
                    con.Open();
                    cmd.CommandText = "insert into tari (tara,id_continent) values ('" + textBoxTara.Text + "',(select id from continente where continent='"+comboBoxContinent.Text+"'))";
                    cmd.ExecuteNonQuery();
                    MessageBox.Show("The country has been added!", "Success!!!",MessageBoxButtons.OK,MessageBoxIcon.None);
                    con.Close();
                    incarcareContinente();
                    textBoxTara.Clear();
                }
                else
                {
                    MessageBox.Show("The country already exists!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                    incarcareContinente();
                    textBoxTara.Clear();
                }
            }
            else
                if (textBoxTara.Text == "" && comboBoxContinent.Text=="")
                    MessageBox.Show("Fill in the fields!", "Warning!!!", MessageBoxButtons.OK,MessageBoxIcon.Exclamation);
                else
                    if (textBoxTara.Text == "")
                        MessageBox.Show("Insert a country!", "Warning!!!", MessageBoxButtons.OK,MessageBoxIcon.Exclamation);
                    else
                        MessageBox.Show("Select a continent!", "Warning!!!", MessageBoxButtons.OK,MessageBoxIcon.Exclamation);

        }

        int verificareReg()
        {
            int a = 0;
            con.Open();
            cmd.CommandText = "select regiune from regiuni where regiune='" + comboBoxNume.Text + "' and id_tara=(select id from tari where tara='" + comboBoxTara.Text + "')";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                a = 1;
            con.Close();
            return a;
        }

        private void buttonAdaugaReg_Click(object sender, EventArgs e)
        {
            if (comboBoxNume.Text != "" && comboBoxTara.Text != "")
            {
                if (verificareReg() == 0)
                {
                    con.Open();
                    cmd.CommandText = "insert into regiuni (regiune,id_tara) values ('" + comboBoxNume.Text + "',(select id from tari where tara='" + comboBoxTara.Text + "'))";
                    cmd.ExecuteNonQuery();
                    MessageBox.Show("The region has been added!", "Success!!!",MessageBoxButtons.OK,MessageBoxIcon.None);
                    con.Close();
                    comboBoxNume.Text = "";
                    incarcareTari();
                }
                else
                {
                    MessageBox.Show("The region already exists!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                    comboBoxNume.Text = "";
                    incarcareTari();
                }
            }
            else
                if (comboBoxNume.Text == "" && comboBoxTara.Text == "")
                    MessageBox.Show("Fill in the fields!", "Warning!!!", MessageBoxButtons.OK,MessageBoxIcon.Exclamation);
                else
                    if (comboBoxNume.Text == "")
                        MessageBox.Show("Insert a region!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                    else
                        MessageBox.Show("Select a country!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);

        }

        int verificareLoc()
        {
            int a = 0;
            con.Open();
            cmd.CommandText = "select localitate from localitati where localitate='" + textBoxLocalitate.Text + "'";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                a = 1;
            con.Close();
            return a;
        }

        private void buttonAdaugaLoc_Click(object sender, EventArgs e)
        {
            if (textBoxLocalitate.Text != "" && comboBoxRegiune.Text != "")
            {
                if (verificareLoc() == 0)
                {
                    con.Open();
                    cmd.CommandText = "insert into localitati (localitate,id_regiune) values ('" + textBoxLocalitate.Text + "',(select id from regiuni where regiune='" + comboBoxRegiune.Text + "' and id_tara=(select id from tari where tara='" + comboBoxTaraLocalitate.Text + "')))";
                    cmd.ExecuteNonQuery();
                    con.Close();
                    con.Open();
                    cmd.CommandText = "insert into distante(id_loc1, id_loc2, distanta) values((select id from localitati where localitate='" + textBoxLocalitate.Text + "'), (select id from localitati where localitate='" + textBoxLocalitate.Text + "'), '" + textBoxDistantaZero.Text + "')";
                    cmd.ExecuteNonQuery();
                    con.Close();
                    textBoxLocalitate.Clear();
                    incarcareTari();
                    incarcareRegiuni();
                    MessageBox.Show("The locality has been added!", "Success!!!", MessageBoxButtons.OK, MessageBoxIcon.None);

                }
                else
                {
                    MessageBox.Show("The locality already exists!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                    textBoxLocalitate.Clear();
                    incarcareTari();
                    incarcareRegiuni();
                }
            }
            else
                if (textBoxLocalitate.Text == "" && comboBoxRegiune.Text == "")
                    MessageBox.Show("Fill in the fields!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                else
                    if (textBoxLocalitate.Text == "")
                        MessageBox.Show("Insert a locality!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                    else
                        MessageBox.Show("Select a region!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);


        }

        private void distanceToolStripMenuItem_Click(object sender, EventArgs e)
        {
            labelInfo.Hide();
            panelAdaugaAeroport.Hide();
            panelStergeAeroport.Hide();
            panelZona.Hide();
            panelDelZona.Hide();
            panelContinent.Hide();
            panelLocalitate.Hide();
            panelTara.Hide();
            panelRegiune.Hide();
            panelCamera.Hide();
            panelTipCamera.Hide();
            panelTipTransport.Hide();
            panelHotel.Hide();
            panelDelCamera.Hide();
            panelDelContinent.Hide();
            panelDelDistanta.Hide();
            panelDelHotel.Hide();
            panelDelLocalitate.Hide();
            panelDelRegiune.Hide();
            panelDelTara.Hide();
            panelDelTipCamera.Hide();
            panelDelTipTransport.Hide();
            panelDistante.Show();
            incarcareLocalitati1();
            
        }

        private void hotelToolStripMenuItem_Click(object sender, EventArgs e)
        {
            labelInfo.Hide();
            panelAdaugaAeroport.Hide();
            panelStergeAeroport.Hide();
            panelZona.Hide();
            panelDelZona.Hide();
            panelContinent.Hide();
            panelLocalitate.Hide();
            panelTara.Hide();
            panelRegiune.Hide();
            panelCamera.Hide();
            panelTipCamera.Hide();
            panelTipTransport.Hide();
            panelDistante.Hide();
            panelDelCamera.Hide();
            panelDelContinent.Hide();
            panelDelDistanta.Hide();
            panelDelHotel.Hide();
            panelDelLocalitate.Hide();
            panelDelRegiune.Hide();
            panelDelTara.Hide();
            panelDelTipCamera.Hide();
            panelDelTipTransport.Hide();
            panelHotel.Show();
            incarcareContinente1();
            comboBoxTaraHotel.Items.Clear();
            comboBoxRegiune.Items.Clear();
            comboBoxLocalitateHotel.Items.Clear();
        }

        private void roomsToolStripMenuItem_Click(object sender, EventArgs e)
        {
            labelInfo.Hide();
            panelAdaugaAeroport.Hide();
            panelStergeAeroport.Hide();
            panelZona.Hide();
            panelDelZona.Hide();
            panelContinent.Hide();
            panelLocalitate.Hide();
            panelTara.Hide();
            panelRegiune.Hide();
            panelTipCamera.Hide();
            panelTipTransport.Hide();
            panelDistante.Hide();
            panelHotel.Hide();
            panelDelCamera.Hide();
            panelDelContinent.Hide();
            panelDelDistanta.Hide();
            panelDelHotel.Hide();
            panelDelLocalitate.Hide();
            panelDelRegiune.Hide();
            panelDelTara.Hide();
            panelDelTipCamera.Hide();
            panelDelTipTransport.Hide();
            panelCamera.Show();
            incarcareHotel();
            incarcareTipCamera();
        }

        private void roomTypeToolStripMenuItem_Click(object sender, EventArgs e)
        {
            labelInfo.Hide();
            panelAdaugaAeroport.Hide();
            panelStergeAeroport.Hide();
            panelZona.Hide();
            panelDelZona.Hide();
            panelContinent.Hide();
            panelLocalitate.Hide();
            panelTara.Hide();
            panelRegiune.Hide();
            panelCamera.Hide();
            panelTipTransport.Hide();
            panelDistante.Hide();
            panelHotel.Hide();
            panelDelCamera.Hide();
            panelDelContinent.Hide();
            panelDelDistanta.Hide();
            panelDelHotel.Hide();
            panelDelLocalitate.Hide();
            panelDelRegiune.Hide();
            panelDelTara.Hide();
            panelDelTipCamera.Hide();
            panelDelTipTransport.Hide();
            panelTipCamera.Show();
        }

        private void transportTypeToolStripMenuItem_Click(object sender, EventArgs e)
        {
            labelInfo.Hide();
            panelZona.Hide();
            panelAdaugaAeroport.Hide();
            panelStergeAeroport.Hide();
            panelDelZona.Hide();
            panelContinent.Hide();
            panelLocalitate.Hide();
            panelTara.Hide();
            panelRegiune.Hide();
            panelCamera.Hide();
            panelTipCamera.Hide();
            panelDistante.Hide();
            panelHotel.Hide();
            panelDelCamera.Hide();
            panelDelContinent.Hide();
            panelDelDistanta.Hide();
            panelDelHotel.Hide();
            panelDelLocalitate.Hide();
            panelDelRegiune.Hide();
            panelDelTara.Hide();
            panelDelTipCamera.Hide();
            panelDelTipTransport.Hide();
            panelTipTransport.Show();
        }

        int verificareDistantaExistenta()
        {
            int a = 0;
            con.Open();
            cmd.CommandText = "select distanta from distante where id_loc1=(select id from localitati where localitate='" + comboBoxLoc1.Text + "') and id_loc2=(select id from localitati where localitate='" + comboBoxLoc2.Text + "')";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                a = 1;
            con.Close();
            return a;
        }

        private void buttonAdaugaDistanta_Click(object sender, EventArgs e)
        {
            if (textBoxDistanta.Text != "" && comboBoxLoc1.Text != "" && comboBoxLoc2.Text != "")
            {
                con.Open();
                cmd.CommandText = "insert into distante(id_loc1, id_loc2, distanta) values((select id from localitati where localitate='" + comboBoxLoc1.Text + "'), (select id from localitati where localitate='" + comboBoxLoc2.Text + "'), '" + textBoxDistanta.Text + "')";
                cmd.ExecuteNonQuery();
                con.Close();
                con.Open();
                cmd.CommandText = "insert into distante(id_loc1, id_loc2, distanta) values((select id from localitati where localitate='" + comboBoxLoc2.Text + "'), (select id from localitati where localitate='" + comboBoxLoc1.Text + "'), '" + textBoxDistanta.Text + "')";
                cmd.ExecuteNonQuery();
                con.Close();
                textBoxDistanta.Clear();
                incarcareLocalitati1();
                incarcareLocalitati2();
                MessageBox.Show("The distance has been added!", "Success!!!", MessageBoxButtons.OK, MessageBoxIcon.None);
            }
            else
                if(comboBoxLoc1.Text == "" && comboBoxLoc2.Text == "" && textBoxDistanta.Text == "")
                    MessageBox.Show("Fill in the fields!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                else
                    if (comboBoxLoc1.Text == "")
                            MessageBox.Show("Select the first locality!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                    else
                        if(comboBoxLoc2.Text == "")
                            MessageBox.Show("Select the second locality!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                        else
                            MessageBox.Show("Insert a distance!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
        }

        int verificareTipCameraExistent()
        {
            int a = 0;
            con.Open();
            cmd.CommandText = "select pret from tip_camere where tip='" + textBoxTipCamera.Text + "'";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                a = 1;
            con.Close();
            return a;
        }

        private void buttonAdaugaTipCamera_Click(object sender, EventArgs e)
        {
            if(textBoxTipCamera.Text != "" && textBoxPretCamera.Text != "" && textBoxCapacitate.Text != "")
            {
                if(verificareTipCameraExistent() == 0)
                {
                    con.Open();
                    cmd.CommandText = "insert into tip_camere(tip,pret,locuri) values('" + textBoxTipCamera.Text + "','" + textBoxPretCamera.Text + "','" + textBoxCapacitate.Text + "')";
                    cmd.ExecuteNonQuery();
                    MessageBox.Show("The room type has been added!", "Success!!!", MessageBoxButtons.OK, MessageBoxIcon.None);
                    con.Close();
                    textBoxTipCamera.Clear();
                    textBoxPretCamera.Clear();
                    textBoxCapacitate.Clear();
                }
                else
                {
                    MessageBox.Show("The room type has been added!", "Success!!!", MessageBoxButtons.OK, MessageBoxIcon.None);
                    textBoxTipCamera.Clear();
                    textBoxPretCamera.Clear();
                    textBoxCapacitate.Clear();
                }
            }
            else
                if(textBoxTipCamera.Text == "" && textBoxPretCamera.Text == "" && textBoxCapacitate.Text == "")
                    MessageBox.Show("Fill in the fields!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                else
                    if(textBoxTipCamera.Text == "")
                        MessageBox.Show("Insert a room type!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                    else
                        if(textBoxPretCamera.Text == "")
                            MessageBox.Show("Insert the price!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                        else
                            MessageBox.Show("Insert the capacity!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
        }

        int verificareTipTransportExistent()
        {
            int a = 0;
            con.Open();
            cmd.CommandText = "select pret_km from tip_transporturi where tip='" + textBoxTipTransport.Text + "'";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                a = 1;
            con.Close();
            return a;
        }

        private void buttonAdaugaTipTransport_Click(object sender, EventArgs e)
        {
            if(textBoxTipTransport.Text != "" && textBoxPretKm.Text != "")
            {
                if(verificareTipTransportExistent() == 0)
                {
                    con.Open();
                    cmd.CommandText = "insert into tip_transporturi(tip,pret_km) values('" + textBoxTipTransport.Text + "','" + textBoxPretKm.Text + "')";
                    cmd.ExecuteNonQuery();
                    MessageBox.Show("The transport type has been added!", "Success!!!", MessageBoxButtons.OK, MessageBoxIcon.None);
                    con.Close();
                    textBoxTipTransport.Clear();
                    textBoxPretKm.Clear();
                }
                else
                {
                    MessageBox.Show("The transport type already exists!", "Warning", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                    textBoxTipTransport.Clear();
                    textBoxPretKm.Clear();
                }
            }
            else
                if(textBoxTipTransport.Text == "" && textBoxPretKm.Text == "")
                    MessageBox.Show("Fill in the fields!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                else
                    if(textBoxTipTransport.Text == "")
                        MessageBox.Show("Insert a transport type!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                    else
                        MessageBox.Show("Insert the price!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
        }

        int verificareEmail()
        {
            con.Open();
            cmd.CommandText = "select email from hoteluri where email='" + textBoxEmailHotel.Text + "'";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
            {
                con.Close();
                return 0;
            }
            con.Close();
            con.Open();
            cmd.CommandText = "select email from clienti where email='" + textBoxEmailHotel.Text + "'";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
            {
                con.Close();
                return 0;
            }
            con.Close();
            return 1;
        }

        private void buttonAdaugaHotel_Click(object sender, EventArgs e)
        {
            int d = verificareEmail();
            bool c = Regex.IsMatch(textBoxEmailHotel.Text,@"^[^@\s]+@[^@\s]+\.[^@\s]+$");
            if(d == 1 && textBoxNumeHotel.Text != "" && textBoxTelefonHotel.Text != "" && textBoxEmailHotel.Text != "" && textBoxZonaHotel.Text != "" && textBoxAdresaHotel.Text != "" && comboBoxContinentHotel.Text != "" && comboBoxTaraHotel.Text != "" && comboBoxRegiuneHotel.Text != "" && comboBoxLocalitateHotel.Text != "" && c)
            {
                con.Open();
                cmd.CommandText = "insert into hoteluri(denumire,telefon,email,id_zona,adresa,id_localitate,id_regiune,id_tara,id_continent) values('" + textBoxNumeHotel.Text + "','" + textBoxTelefonHotel.Text + "','" + textBoxEmailHotel.Text + "',(select id from zone where zona='" + textBoxZonaHotel.Text + "' and id_loc=(select id from localitati where localitate='" + comboBoxLocalitateHotel.Text + "')),'" + textBoxAdresaHotel.Text + "',(select id from localitati where localitate='" + comboBoxLocalitateHotel.Text + "'),(select id from regiuni where regiune='" + comboBoxRegiuneHotel.Text + "' and id_tara=(select id from tari where tara='" + comboBoxTaraHotel.Text + "')),(select id from tari where tara='" + comboBoxTaraHotel.Text + "'),(select id from continente where continent='" + comboBoxContinentHotel.Text + "'))";
                cmd.ExecuteNonQuery();
                MessageBox.Show("The hotel has been added!", "Success!!!", MessageBoxButtons.OK, MessageBoxIcon.None);
                con.Close();
                textBoxNumeHotel.Clear();
                textBoxTelefonHotel.Clear();
                textBoxEmailHotel.Clear();
                comboBoxRegiuneHotel.Text = "";
                textBoxAdresaHotel.Clear();
                incarcareContinente1();
                incarcareTari1();
                incarcareRegiuni1();
                incarcareLocalitati3();
                textBoxZonaHotel.Text = "";
                comboBoxContinentHotel.Text = "";
                comboBoxTaraHotel.Text = "";
                comboBoxRegiuneHotel.Text = "";
                comboBoxLocalitateHotel.Text = "";
            }
            else
                if (textBoxNumeHotel.Text == "" && textBoxTelefonHotel.Text == "" && textBoxEmailHotel.Text == "" && textBoxZonaHotel.Text == "" && textBoxAdresaHotel.Text == "" && comboBoxContinentHotel.Text == "" && comboBoxTaraHotel.Text == "" && comboBoxRegiuneHotel.Text == "" && comboBoxLocalitateHotel.Text == "")
                    MessageBox.Show("Fill in the fields!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                else
                    if (textBoxNumeHotel.Text == "")
                        MessageBox.Show("Insert the hotel's name!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                    else
                        if(textBoxTelefonHotel.Text == "")
                            MessageBox.Show("Insert a phone number!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                        else
                            if(textBoxEmailHotel.Text == "")
                                MessageBox.Show("Insert an email to the hotel!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                            else
                                if(textBoxAdresaHotel.Text == "")
                                    MessageBox.Show("Insert an address!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                                else
                                    if(comboBoxContinentHotel.Text == "")
                                        MessageBox.Show("Select a continent!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                                    else
                                        if(comboBoxTaraHotel.Text == "")
                                            MessageBox.Show("Select a country!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                                        else
                                            if(comboBoxRegiuneHotel.Text == "")
                                                MessageBox.Show("Select a region!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                                            else
                                                if(comboBoxLocalitateHotel.Text == "")
                                                    MessageBox.Show("Select the locality!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                                                else
                                                    if(!c)
                                                        MessageBox.Show("Invalid email format!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                                                    else
                                                        if(d == 0)
                                                            MessageBox.Show("The email already exists!", "Error!!!", MessageBoxButtons.OK, MessageBoxIcon.Error);
        }

        int verificareNr()
        {
            int a = 0;
            con.Open();
            cmd.CommandText = "select nr_camera from camere where nr_camera='" + textBoxNrCamera.Text + "' and id_hotel=(select id from hoteluri where denumire='" + comboBoxHotel.Text + "')";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                a = 1;
            con.Close();
            return a;
        }

        private void buttonAdaugaCamera_Click(object sender, EventArgs e)
        {
            if(textBoxNrCamera.Text != "" && comboBoxTipCamera.Text != "" && comboBoxHotel.Text != "")
                if(verificareNr() == 0)
                {
                    con.Open();
                    cmd.CommandText = "insert into camere(nr_camera,id_tip_camera,id_hotel) values('" + textBoxNrCamera.Text + "',(select id from tip_camere where tip='" + comboBoxTipCamera.Text + "'),(select id from hoteluri where denumire='" + comboBoxHotel.Text + "'))";
                    cmd.ExecuteNonQuery();
                    MessageBox.Show("The room has been added!", "Success!!!", MessageBoxButtons.OK, MessageBoxIcon.None);
                    con.Close();
                    textBoxNrCamera.Clear();
                    incarcareHotel();
                    incarcareTipCamera();
                }
                else
                {
                    MessageBox.Show("The room number already exists!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                    textBoxNrCamera.Clear();
                    incarcareHotel();
                    incarcareTipCamera();
                }
            else
                if(textBoxNrCamera.Text == "" && comboBoxTipCamera.Text == "" && comboBoxHotel.Text == "")
                    MessageBox.Show("Fill in the fields!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                else
                    if(textBoxNrCamera.Text == "")
                        MessageBox.Show("Insert a room number!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                    else
                        if(comboBoxTipCamera.Text != "")
                            MessageBox.Show("Insert a room type!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                        else
                            MessageBox.Show("Insert a hotel!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
        }

        private void continentToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            labelInfo.Hide();
            panelAdaugaAeroport.Hide();
            panelStergeAeroport.Hide();
            panelZona.Hide();
            panelDelZona.Hide();
            panelContinent.Hide();
            panelLocalitate.Hide();
            panelTara.Hide();
            panelRegiune.Hide();
            panelCamera.Hide();
            panelTipTransport.Hide();
            panelDistante.Hide();
            panelHotel.Hide();
            panelTipCamera.Hide();
            panelDelCamera.Hide();
            panelDelDistanta.Hide();
            panelDelHotel.Hide();
            panelDelLocalitate.Hide();
            panelDelRegiune.Hide();
            panelDelTara.Hide();
            panelDelTipCamera.Hide();
            panelDelTipTransport.Hide();
            panelDelContinent.Show();
            incarcareContinente();
        }

        private void countryToolStripMenuItem_Click(object sender, EventArgs e)
        {
            labelInfo.Hide();
            panelAdaugaAeroport.Hide();
            panelStergeAeroport.Hide();
            panelZona.Hide();
            panelDelZona.Hide();
            panelContinent.Hide();
            panelLocalitate.Hide();
            panelTara.Hide();
            panelRegiune.Hide();
            panelCamera.Hide();
            panelTipTransport.Hide();
            panelDistante.Hide();
            panelHotel.Hide();
            panelTipCamera.Hide();
            panelDelCamera.Hide();
            panelDelContinent.Hide();
            panelDelDistanta.Hide();
            panelDelHotel.Hide();
            panelDelLocalitate.Hide();
            panelDelRegiune.Hide();
            panelDelTipCamera.Hide();
            panelDelTipTransport.Hide();
            panelDelTara.Show();
            incarcareTari();
        }

        private void regionToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            labelInfo.Hide();
            panelAdaugaAeroport.Hide();
            panelStergeAeroport.Hide();
            panelZona.Hide();
            panelDelZona.Hide();
            panelContinent.Hide();
            panelLocalitate.Hide();
            panelTara.Hide();
            panelRegiune.Hide();
            panelCamera.Hide();
            panelTipTransport.Hide();
            panelDistante.Hide();
            panelHotel.Hide();
            panelTipCamera.Hide();
            panelDelCamera.Hide();
            panelDelContinent.Hide();
            panelDelDistanta.Hide();
            panelDelHotel.Hide();
            panelDelLocalitate.Hide();
            panelDelTara.Hide();
            panelDelTipCamera.Hide();
            panelDelTipTransport.Hide();
            panelDelRegiune.Show();
            incarcareTari();
        }

        private void localityToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            labelInfo.Hide();
            panelAdaugaAeroport.Hide();
            panelStergeAeroport.Hide();
            panelZona.Hide();
            panelDelZona.Hide();
            panelContinent.Hide();
            panelLocalitate.Hide();
            panelTara.Hide();
            panelRegiune.Hide();
            panelCamera.Hide();
            panelTipTransport.Hide();
            panelDistante.Hide();
            panelHotel.Hide();
            panelTipCamera.Hide();
            panelDelCamera.Hide();
            panelDelContinent.Hide();
            panelDelDistanta.Hide();
            panelDelHotel.Hide();
            panelDelRegiune.Hide();
            panelDelTara.Hide();
            panelDelTipCamera.Hide();
            panelDelTipTransport.Hide();
            panelDelLocalitate.Show();
            incarcareLocalitati1();
        }

        private void distanceToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            labelInfo.Hide();
            panelAdaugaAeroport.Hide();
            panelStergeAeroport.Hide();
            panelZona.Hide();
            panelDelZona.Hide();
            panelContinent.Hide();
            panelLocalitate.Hide();
            panelTara.Hide();
            panelRegiune.Hide();
            panelCamera.Hide();
            panelTipTransport.Hide();
            panelDistante.Hide();
            panelHotel.Hide();
            panelTipCamera.Hide();
            panelDelCamera.Hide();
            panelDelContinent.Hide();
            panelDelHotel.Hide();
            panelDelLocalitate.Hide();
            panelDelRegiune.Hide();
            panelDelTara.Hide();
            panelDelTipCamera.Hide();
            panelDelTipTransport.Hide();
            panelDelDistanta.Show();
            incarcareDistanta();
        }

        private void hotelToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            labelInfo.Hide();
            panelAdaugaAeroport.Hide();
            panelStergeAeroport.Hide();
            panelZona.Hide();
            panelDelZona.Hide();
            panelContinent.Hide();
            panelLocalitate.Hide();
            panelTara.Hide();
            panelRegiune.Hide();
            panelCamera.Hide();
            panelTipTransport.Hide();
            panelDistante.Hide();
            panelHotel.Hide();
            panelTipCamera.Hide();
            panelDelCamera.Hide();
            panelDelContinent.Hide();
            panelDelDistanta.Hide();
            panelDelLocalitate.Hide();
            panelDelRegiune.Hide();
            panelDelTara.Hide();
            panelDelTipCamera.Hide();
            panelDelTipTransport.Hide();
            panelDelHotel.Show();
            incarcareHotel();
        }

        private void roomToolStripMenuItem_Click(object sender, EventArgs e)
        {
            labelInfo.Hide();
            panelAdaugaAeroport.Hide();
            panelStergeAeroport.Hide();
            panelZona.Hide();
            panelDelZona.Hide();
            panelContinent.Hide();
            panelLocalitate.Hide();
            panelTara.Hide();
            panelRegiune.Hide();
            panelCamera.Hide();
            panelTipTransport.Hide();
            panelDistante.Hide();
            panelHotel.Hide();
            panelTipCamera.Hide();
            panelDelContinent.Hide();
            panelDelDistanta.Hide();
            panelDelHotel.Hide();
            panelDelLocalitate.Hide();
            panelDelRegiune.Hide();
            panelDelTara.Hide();
            panelDelTipCamera.Hide();
            panelDelTipTransport.Hide();
            panelDelCamera.Show();
            incarcareHotel();
        }

        private void roomTypeToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            labelInfo.Hide();
            panelAdaugaAeroport.Hide();
            panelStergeAeroport.Hide();
            panelZona.Hide();
            panelDelZona.Hide();
            panelContinent.Hide();
            panelLocalitate.Hide();
            panelTara.Hide();
            panelRegiune.Hide();
            panelCamera.Hide();
            panelTipTransport.Hide();
            panelDistante.Hide();
            panelHotel.Hide();
            panelTipCamera.Hide();
            panelDelCamera.Hide();
            panelDelContinent.Hide();
            panelDelDistanta.Hide();
            panelDelHotel.Hide();
            panelDelLocalitate.Hide();
            panelDelRegiune.Hide();
            panelDelTara.Hide();
            panelDelTipTransport.Hide();
            panelDelTipCamera.Show();
            incarcareTipCamera();
        }

        private void transportTypeToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            labelInfo.Hide();
            panelZona.Hide();
            panelAdaugaAeroport.Hide();
            panelStergeAeroport.Hide();
            panelDelZona.Hide();
            panelContinent.Hide();
            panelLocalitate.Hide();
            panelTara.Hide();
            panelRegiune.Hide();
            panelCamera.Hide();
            panelTipTransport.Hide();
            panelDistante.Hide();
            panelHotel.Hide();
            panelTipCamera.Hide();
            panelDelCamera.Hide();
            panelDelContinent.Hide();
            panelDelDistanta.Hide();
            panelDelHotel.Hide();
            panelDelLocalitate.Hide();
            panelDelRegiune.Hide();
            panelDelTara.Hide();
            panelDelTipCamera.Hide();
            panelDelTipTransport.Show();
            incarcareTipTransport();
        }

        private void buttonDelContinent_Click(object sender, EventArgs e)
        {
            if(comboBoxDelContinent.Text != "")
            {
                con.Open();
                cmd.CommandText = "delete from continente where continent='" + comboBoxDelContinent.Text + "'";
                cmd.ExecuteNonQuery();
                MessageBox.Show("The continent has been deleted!", "Success!!!", MessageBoxButtons.OK, MessageBoxIcon.None);
                con.Close();
                incarcareContinente();
                comboBoxDelContinent.Text = "";
            }
            else
                   MessageBox.Show("Select a continent to delete!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
        }

        private void buttonDelTara_Click(object sender, EventArgs e)
        {
            if (comboBoxDelTara.Text != "")
            {
                con.Open();
                cmd.CommandText = "delete from tari where tara='" + comboBoxDelTara.Text + "'";
                cmd.ExecuteNonQuery();
                MessageBox.Show("The country has been deleted!", "Success!!!", MessageBoxButtons.OK, MessageBoxIcon.None);
                con.Close();
                incarcareTari();
                comboBoxDelTara.Text = "";
            }
            else
                MessageBox.Show("Select a country to delete!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
        }

        private void buttonDelRegiune_Click(object sender, EventArgs e)
        {
            if (comboBoxDelRegiune.Text != "")
            {
                con.Open();
                cmd.CommandText = "select id from tari where tara='" + comboBoxTaraDelRegiune.Text + "'";
                dr = cmd.ExecuteReader();
                if (dr.HasRows)
                    while (dr.Read())
                        textBoxHotelD.Text = dr[0].ToString();
                con.Close();
                con.Open();
                cmd.CommandText = "delete from regiuni where regiune='" + comboBoxDelRegiune.Text + "' and id_tara='" + textBoxHotelD.Text + "'";
                cmd.ExecuteNonQuery();
                MessageBox.Show("The region has been deleted!", "Success!!!", MessageBoxButtons.OK, MessageBoxIcon.None);
                con.Close();
                incarcareTari();
                incarcareRegiuni();
                comboBoxDelRegiune.Text = "";
            }
            else
                MessageBox.Show("Select a region to delete!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
        }

        private void buttonDelLocalitate_Click(object sender, EventArgs e)
        {
            if (comboBoxDelLocalitate.Text != "")
            {
                con.Open();
                cmd.CommandText = "delete from localitati where localitate='" + comboBoxDelLocalitate.Text + "'";
                cmd.ExecuteNonQuery();
                MessageBox.Show("The locality has been deleted!", "Success!!!", MessageBoxButtons.OK, MessageBoxIcon.None);
                con.Close();
                incarcareLocalitati1();
                comboBoxDelLocalitate.Text = "";
            }
            else
                MessageBox.Show("Select a locality to delete!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
        }

        private void buttonDelHotel_Click(object sender, EventArgs e)
        {
            if (comboBoxDelHotel.Text != "")
            {
                con.Open();
                cmd.CommandText = "delete from hoteluri where denumire='" + comboBoxDelHotel.Text + "'";
                cmd.ExecuteNonQuery();
                MessageBox.Show("The hotel has been deleted!", "Success!!!", MessageBoxButtons.OK, MessageBoxIcon.None);
                con.Close();
                incarcareHotel();
                comboBoxDelHotel.Text = "";
            }
            else
                MessageBox.Show("Select a hotel to delete!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
        }

        private void buttonDelTipCamera_Click(object sender, EventArgs e)
        {
            if (comboBoxDelTipCamera.Text != "")
            {
                con.Open();
                cmd.CommandText = "delete from tip_camere where tip='" + comboBoxDelTipCamera.Text + "'";
                cmd.ExecuteNonQuery();
                MessageBox.Show("The room type has been deleted!", "Success!!!", MessageBoxButtons.OK, MessageBoxIcon.None);
                con.Close();
                incarcareTipCamera();
                comboBoxDelTipCamera.Text = "";
            }
            else
                MessageBox.Show("Select a room type to delete!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
        }

        private void buttonDelTipTransport_Click(object sender, EventArgs e)
        {
            if (comboBoxDelTipTransport.Text != "")
            {
                con.Open();
                cmd.CommandText = "delete from tip_transporturi where tip='" + comboBoxDelTipTransport.Text + "'";
                cmd.ExecuteNonQuery();
                MessageBox.Show("The transport type has been deleted!", "Success!!!", MessageBoxButtons.OK, MessageBoxIcon.None);
                con.Close();
                incarcareTipTransport();
                comboBoxDelTipTransport.Text = "";
            }
            else
                MessageBox.Show("Select a transport type to delete!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
        }

        private void buttonDelDistanta_Click(object sender, EventArgs e)
        {
            if (comboBoxDelLoc1.Text != "" && comboBoxDelLoc2.Text != "")
            {
                con.Open();
                cmd.CommandText = "select id from localitati where localitate='" + comboBoxDelLoc1.Text + "'";
                dr = cmd.ExecuteReader();
                if (dr.HasRows)
                    while (dr.Read())
                        textBoxLoc1D.Text = dr[0].ToString();
                con.Close();
                con.Open();
                cmd.CommandText = "select id from localitati where localitate='" + comboBoxDelLoc2.Text + "'";
                dr = cmd.ExecuteReader();
                if (dr.HasRows)
                    while (dr.Read())
                        textBoxLoc2D.Text = dr[0].ToString();
                con.Close();
                con.Open();
                cmd.CommandText = "delete from distante where id_loc1='" + textBoxLoc1D.Text + "' and id_loc2='" + textBoxLoc2D.Text + " '";
                cmd.ExecuteNonQuery();
                con.Close();
                con.Open();
                cmd.CommandText = "delete from distante where id_loc1='" + textBoxLoc2D.Text + "' and id_loc2='" + textBoxLoc1D.Text + " '";
                cmd.ExecuteNonQuery();
                MessageBox.Show("The distance has been deleted!", "Success!!!", MessageBoxButtons.OK, MessageBoxIcon.None);
                con.Close();
                incarcareDistanta();
                comboBoxDelLoc2.Items.Clear();
                textBoxDelDistanta.Clear();
                textBoxLoc1D.Clear();
                textBoxLoc2D.Clear();
            }
            else
                MessageBox.Show("Select a distance to delete!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
        }

        private void buttonDelCamera_Click(object sender, EventArgs e)
        {
            if (comboBoxDelCamera.Text != "" && comboBoxHotelD.Text != "")
            {
                con.Open();
                cmd.CommandText = "select id from hoteluri where denumire='" + comboBoxHotelD.Text + "'";
                dr = cmd.ExecuteReader();
                if (dr.HasRows)
                    while (dr.Read())
                        textBoxHotelD.Text = dr[0].ToString();
                con.Close();
                con.Open();
                cmd.CommandText = "delete from camere where nr_camera='" + comboBoxDelCamera.Text + "' and id_hotel='" + textBoxHotelD.Text + "'";
                cmd.ExecuteNonQuery();
                MessageBox.Show("The room has been deleted!", "Success!!!", MessageBoxButtons.OK, MessageBoxIcon.None);
                con.Close();
                incarcareCamera();
                comboBoxDelCamera.Text = "";
            }
            else
                MessageBox.Show("Select a room to delete!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
        }

        private void comboBoxDelLoc1_SelectedIndexChanged(object sender, EventArgs e)
        {
            incarcareDistanta2();
        }

        private void comboBoxDelLoc2_SelectedIndexChanged(object sender, EventArgs e)
        {
            textBoxDelDistanta.Clear();
            con.Open();
            cmd.CommandText = "select distinct distanta from distante where (select localitate from localitati where id=id_loc1)='" + comboBoxDelLoc1.Text + "' and (select localitate from localitati where id=id_loc2)='" + comboBoxDelLoc2.Text + "'";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    textBoxDelDistanta.Text = dr[0].ToString();
            con.Close();
        }

        private void comboBoxHotelD_SelectedIndexChanged(object sender, EventArgs e)
        {
            incarcareCamera();
        }

        private void comboBoxDelCamera_SelectedIndexChanged(object sender, EventArgs e)
        {
            textBoxTipCameraD.Clear();
            con.Open();
            cmd.CommandText = "select tip from tip_camere where id=(select id_tip_camera from camere where nr_camera='" + comboBoxDelCamera.Text + "' and id_hotel=(select id from hoteluri where denumire='" + comboBoxHotelD.Text + "'))";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while(dr.Read())
                    textBoxTipCameraD.Text = dr[0].ToString();
            con.Close();
        }

        private void comboBoxContinentHotel_SelectedIndexChanged(object sender, EventArgs e)
        {
            comboBoxTaraHotel.Text = "";
            comboBoxTaraHotel.Items.Clear();
            comboBoxRegiuneHotel.Text = "";
            comboBoxRegiuneHotel.Items.Clear();
            comboBoxLocalitateHotel.Text = "";
            comboBoxLocalitateHotel.Items.Clear();
            textBoxZonaHotel.Text = "";
            textBoxZonaHotel.Clear();
            con.Open();
            cmd.CommandText = "select tara from tari where id_continent=(select id from continente where continent='" + comboBoxContinentHotel.Text + "')";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxTaraHotel.Items.Add(dr[0].ToString());
            con.Close();
        }

        private void comboBoxTaraHotel_SelectedIndexChanged(object sender, EventArgs e)
        {
            comboBoxRegiuneHotel.Text = "";
            comboBoxRegiuneHotel.Items.Clear();
            comboBoxLocalitateHotel.Text = "";
            comboBoxLocalitateHotel.Items.Clear();
            textBoxZonaHotel.Text = "";
            textBoxZonaHotel.Clear();
            con.Open();
            cmd.CommandText = "select regiune from regiuni where id_tara=(select id from tari where tara='" + comboBoxTaraHotel.Text + "')";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxRegiuneHotel.Items.Add(dr[0].ToString());
            con.Close();
        }

        private void comboBoxRegiuneHotel_SelectedIndexChanged(object sender, EventArgs e)
        {
            comboBoxLocalitateHotel.Text = "";
            comboBoxLocalitateHotel.Items.Clear();
            textBoxZonaHotel.Text = "";
            textBoxZonaHotel.Clear();
            con.Open();
            cmd.CommandText = "select localitate from localitati where id_regiune=(select id from regiuni where regiune='" + comboBoxRegiuneHotel.Text + "' and id_tara=(select id from tari where tara='" + comboBoxTaraHotel.Text + "'))";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxLocalitateHotel.Items.Add(dr[0].ToString());
            con.Close();
        }

        private void comboBoxLocalitateHotel_SelectedIndexChanged(object sender, EventArgs e)
        {
            textBoxZonaHotel.Text = "";
            textBoxZonaHotel.Clear();
            con.Open();
            cmd.CommandText = "select zona from zone where id_loc=(select id from localitati where localitate='" + comboBoxLocalitateHotel.Text + "')";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    textBoxZonaHotel.Text = dr[0].ToString();
            con.Close();
        }

        void incarcareZona()
        {
            comboBoxDelZona.Items.Clear();
            con.Open();
            cmd.CommandText = "select zona from zone";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxDelZona.Items.Add(dr[0].ToString());
            con.Close();
        }

        private void zoneToolStripMenuItem_Click(object sender, EventArgs e)
        {
            labelInfo.Hide();
            panelAdaugaAeroport.Hide();
            panelStergeAeroport.Hide();
            panelContinent.Hide();
            panelLocalitate.Hide();
            panelTara.Hide();
            panelRegiune.Hide();
            panelCamera.Hide();
            panelTipCamera.Hide();
            panelTipTransport.Hide();
            panelDistante.Hide();
            panelHotel.Hide();
            panelDelCamera.Hide();
            panelDelContinent.Hide();
            panelDelDistanta.Hide();
            panelDelHotel.Hide();
            panelDelLocalitate.Hide();
            panelDelRegiune.Hide();
            panelDelTara.Hide();
            panelDelTipCamera.Hide();
            panelDelTipTransport.Hide();
            panelDelZona.Hide();
            panelZona.Show();
            incarcareLocalitati1();
        }

        private void zoneToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            panelAdaugaAeroport.Hide();
            panelStergeAeroport.Hide();
            labelInfo.Hide();
            panelContinent.Hide();
            panelLocalitate.Hide();
            panelTara.Hide();
            panelRegiune.Hide();
            panelCamera.Hide();
            panelTipCamera.Hide();
            panelTipTransport.Hide();
            panelDistante.Hide();
            panelHotel.Hide();
            panelDelCamera.Hide();
            panelDelContinent.Hide();
            panelDelDistanta.Hide();
            panelDelHotel.Hide();
            panelDelLocalitate.Hide();
            panelDelRegiune.Hide();
            panelDelTara.Hide();
            panelDelTipCamera.Hide();
            panelDelTipTransport.Hide();
            panelZona.Hide();
            panelDelZona.Show();
            incarcareZona();
        }

        int verificareZonaExistenta()
        {
            int a = 0;
            con.Open();
            cmd.CommandText = "select localitate from localitati where id in (select id_loc from zone) and localitate='" + comboBoxLocalitateZona.Text + "'";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                a = 1;
            con.Close();
            return a;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            if(comboBoxZona.Text != "" && comboBoxLocalitateZona.Text != "")
            {
                if(verificareZonaExistenta() == 0)
                {
                    con.Open();
                    cmd.CommandText = "insert into zone(zona,id_loc) values('" + comboBoxZona.Text + "',(select id from localitati where localitate='" + comboBoxLocalitateZona.Text + "'))";
                    cmd.ExecuteNonQuery();
                    MessageBox.Show("The zone has been added!", "Success!!!", MessageBoxButtons.OK, MessageBoxIcon.None);
                    con.Close();
                    comboBoxZona.Text = "";
                    incarcareLocalitati1();
                }
                else
                {
                    MessageBox.Show("The zone already exists!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                    comboBoxZona.Text = "";
                    incarcareLocalitati1();
                }
            }
            else
                if(comboBoxZona.Text == "" && comboBoxLocalitateZona.Text == "")
                    MessageBox.Show("Fill in the fields!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                else
                    if(comboBoxZona.Text == "")
                        MessageBox.Show("Insert a zone!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                    else
                        if(comboBoxLocalitateZona.Text == "")
                            MessageBox.Show("Select a locality!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
        }

        private void button1_Click_1(object sender, EventArgs e)
        {
            if(comboBoxDelZona.Text != "")
            {
                con.Open();
                cmd.CommandText = "delete from zone where zona='" + comboBoxDelZona.Text + "'";
                cmd.ExecuteNonQuery();
                MessageBox.Show("The zone has been deleted!", "Success!!!", MessageBoxButtons.OK, MessageBoxIcon.None);
                con.Close();
                incarcareZona();
            }
            else
                MessageBox.Show("Select a zone to delete!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
        }

        private void textBoxPretKm_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsDigit(e.KeyChar) && !char.IsControl(e.KeyChar) && e.KeyChar != '.')
            {
                e.Handled = true;
                MessageBox.Show("Insert only numbers and the character '.' in this field!", "Error!!!", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            else
                e.Handled = false;
        }

        private void textBoxDistanta_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsDigit(e.KeyChar) && !char.IsControl(e.KeyChar) && e.KeyChar != '.')
            {
                e.Handled = true;
                MessageBox.Show("Insert only numbers and the character '.' in this field!", "Error!!!", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            else
                e.Handled = false;
        }

        private void textBoxTelefonHotel_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsDigit(e.KeyChar) && !char.IsControl(e.KeyChar))
            {
                e.Handled = true;
                MessageBox.Show("Insert only numbers in this field!", "Error!!!", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            else
                e.Handled = false;
        }

        private void textBoxNrCamera_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsDigit(e.KeyChar) && !char.IsControl(e.KeyChar))
            {
                e.Handled = true;
                MessageBox.Show("Insert only numbers in this field!", "Error!!!", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            else
                e.Handled = false;
        }

        private void textBoxPretCamera_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsDigit(e.KeyChar) && !char.IsControl(e.KeyChar) && e.KeyChar != '.')
            {
                e.Handled = true;
                MessageBox.Show("Insert only numbers and the character '.' in this field!", "Error!!!", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            else
                e.Handled = false;
        }

        private void textBoxCapacitate_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsDigit(e.KeyChar) && !char.IsControl(e.KeyChar))
            {
                e.Handled = true;
                MessageBox.Show("Insert only numbers in this field!", "Error!!!", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            else
                e.Handled = false;
        }

        private void textBoxEmailHotel_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (char.IsWhiteSpace(e.KeyChar))
            {
                e.Handled = true;
                MessageBox.Show("Space is not allowed in this field!", "Error!!!", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            else
                e.Handled = false;
        }

        private void airportToolStripMenuItem_Click(object sender, EventArgs e)
        {
            labelInfo.Hide();
            panelContinent.Hide();
            panelLocalitate.Hide();
            panelTara.Hide();
            panelRegiune.Hide();
            panelCamera.Hide();
            panelTipCamera.Hide();
            panelTipTransport.Hide();
            panelDistante.Hide();
            panelHotel.Hide();
            panelDelCamera.Hide();
            panelDelContinent.Hide();
            panelDelDistanta.Hide();
            panelDelHotel.Hide();
            panelDelLocalitate.Hide();
            panelDelRegiune.Hide();
            panelDelTara.Hide();
            panelDelTipCamera.Hide();
            panelDelTipTransport.Hide();
            panelZona.Hide();
            panelDelZona.Hide();
            panelStergeAeroport.Hide();
            panelAdaugaAeroport.Show();
            incarcareLocalitatiAeroport();
        }

        private void airportToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            labelInfo.Hide();
            panelContinent.Hide();
            panelLocalitate.Hide();
            panelTara.Hide();
            panelRegiune.Hide();
            panelCamera.Hide();
            panelTipCamera.Hide();
            panelTipTransport.Hide();
            panelDistante.Hide();
            panelHotel.Hide();
            panelDelCamera.Hide();
            panelDelContinent.Hide();
            panelDelDistanta.Hide();
            panelDelHotel.Hide();
            panelDelLocalitate.Hide();
            panelDelRegiune.Hide();
            panelDelTara.Hide();
            panelDelTipCamera.Hide();
            panelDelTipTransport.Hide();
            panelZona.Hide();
            panelDelZona.Hide();
            panelAdaugaAeroport.Hide();
            panelStergeAeroport.Show();
            incarcareAeroporturi();
        }

        void incarcareAeroporturi()
        {
            comboBoxStergeAeroport.Items.Clear();
            con.Open();
            cmd.CommandText = "select localitate from localitati where id in (select id_localitate from aeroporturi)";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxStergeAeroport.Items.Add(dr[0].ToString());
            con.Close();
        }

        private void buttonAdaugaAeroport_Click(object sender, EventArgs e)
        { 
            if (comboBoxAdaugaAeroport.Text != "")
            {
                con.Open();
                cmd.CommandText = "insert into aeroporturi(id_localitate) values((select id from localitati where localitate='" + comboBoxAdaugaAeroport.Text + "'))";
                cmd.ExecuteNonQuery();
                con.Close();
                incarcareLocalitatiAeroport();
                MessageBox.Show("The airport has been added!", "Success!!!", MessageBoxButtons.OK, MessageBoxIcon.None);
            }
            else
                MessageBox.Show("Select a locality!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
        }

        private void buttonStergeAeroport_Click(object sender, EventArgs e)
        {
            if (comboBoxStergeAeroport.Text != "")
            {
                con.Open();
                cmd.CommandText = "select id from localitati where localitate ='" + comboBoxStergeAeroport.Text + "'";
                dr = cmd.ExecuteReader();
                if (dr.HasRows)
                    while (dr.Read())
                        textBoxStergeAeroport.Text = dr[0].ToString();
                con.Close();
                con.Open();
                cmd.CommandText = "delete from aeroporturi where id_localitate='" + textBoxStergeAeroport.Text + "'";
                cmd.ExecuteNonQuery();
                con.Close();
                MessageBox.Show("The airport has been deleted!", "Success!!!", MessageBoxButtons.OK, MessageBoxIcon.None);
                incarcareAeroporturi();
            }
            else
                MessageBox.Show("Select an airport to delete!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
        }

        private void comboBoxLoc1_SelectedIndexChanged(object sender, EventArgs e)
        {
            incarcareLocalitati2();
        }

        private void comboBoxTaraLocalitate_SelectedIndexChanged(object sender, EventArgs e)
        {
            textBoxTaraDinCombo.Text = comboBoxTaraLocalitate.Text;
            incarcareRegiuni();
        }

        private void comboBoxTaraDelRegiune_SelectedIndexChanged(object sender, EventArgs e)
        {
            textBoxTaraDinCombo.Text = comboBoxTaraDelRegiune.Text;
            incarcareRegiuni();
        }
    }
}
