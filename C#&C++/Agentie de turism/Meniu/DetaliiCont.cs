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
    public partial class DetaliiCont : Form
    {
        int k,c,yPret,idRezervareMinim,idRezervareMaxim,idResetRezervareMinim,idResetRezervareMaxim,zileRezervari;

        float pretTotal;

        string[] camereRezervare, camereRezervare2;

        SqlConnection con = new SqlConnection(@"Data Source=(LocalDB)\MSSQLLocalDB;AttachDbFilename=D:\CSharp\Agentie de turism\Agentie de turism\Database1.mdf;Integrated Security=True");
        SqlCommand cmd = new SqlCommand();
        SqlDataReader dr;

        public DetaliiCont(string Cont)
        {
            InitializeComponent();
            textBoxCont.Text = Cont.ToString();
        }

        void reincarcareRezervari()
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
                            textBoxCamereRezervare.Text = dr[0].ToString();
                    con.Close();
                    dateTimePickerData.Value = Convert.ToDateTime(textBoxCamereRezervare.Text);
                    con.Open();
                    cmd.CommandText = "select nr_zile from rezervari where id='" + i.ToString() + "'";
                    dr = cmd.ExecuteReader();
                    if (dr.HasRows)
                        while (dr.Read())
                            zileRezervari = int.Parse(dr[0].ToString());
                    con.Close();
                    if (dateTimePickerData.Value.AddDays(zileRezervari) <= DateTime.Today)
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
                        cmd.CommandText = "insert into resetrezervari(data, nr_zile, id_camera, id_client, id_tip_transport, id_hotel, tip_bilet, id_distanta) values('" + dateTimePickerData.Text + "', '" + zileRezervari.ToString() + "', (select id_camera from rezervari where id='" + i.ToString() + "'), (select id_client from rezervari where id='" + i.ToString() + "'), (select id_tip_transport from rezervari where id='" + i.ToString() + "'), (select id_hotel from rezervari where id='" + i.ToString() + "'), (select tip_bilet from rezervari where id='" + i.ToString() + "'), (select id_distanta from rezervari where id='" + i.ToString() + "'))";
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
                if (textBoxIdRezervareMaxim.Text != "")
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

        void stergeRezervare()
        {
            con.Open();
            cmd.CommandText = "delete from rezervari where id='" + comboBoxRezervari.Text + "'";
            cmd.ExecuteNonQuery();
            con.Close();
            con.Open();
            cmd.CommandText = "select max(id) from rezervari";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    textBoxIdRezervareMaxim.Text = dr[0].ToString();
            con.Close();
            if(textBoxIdRezervareMaxim.Text != "")
            {
                idResetRezervareMaxim = int.Parse(textBoxIdRezervareMaxim.Text);
                con.Open();
                cmd.CommandText = "select min(id) from rezervari";
                dr = cmd.ExecuteReader();
                if (dr.HasRows)
                    while (dr.Read())
                        idRezervareMinim = int.Parse(dr[0].ToString());
                con.Close();
                for(int i = idResetRezervareMinim; i <= idResetRezervareMaxim; i++)
                {
                    if(i != int.Parse(comboBoxRezervari.Text))
                    {
                        con.Open();
                        cmd.CommandText = "insert into resetrezervari(data, nr_zile, id_camera, id_client, id_tip_transport, id_hotel, tip_bilet, id_distanta) values((select data from rezervari where id='" + i.ToString() + "'), (select nr_zile from rezervari where id='" + i.ToString() + "'), (select id_camera from rezervari where id='" + i.ToString() + "'), (select id_client from rezervari where id='" + i.ToString() + "'), (select id_tip_transport from rezervari where id='" + i.ToString() + "'), (select id_hotel from rezervari where id='" + i.ToString() + "'), (select tip_bilet from rezervari where id='" + i.ToString() + "'), (select id_distanta from rezervari where id='" + i.ToString() + "'))";
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
                if (textBoxIdRezervareMaxim.Text != "")
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

        void pozitieChenare()
        {
            textBoxEmail.Location = new Point((panelDetaliiCont.Width - textBoxEmail.Width) / 2, (panelDetaliiCont.Height - textBoxEmail.Height) / 2 - 50);
            textBoxTelefon.Location = new Point(textBoxEmail.Location.X, textBoxEmail.Location.Y - 32);
            dateTimePickerDataN.Location = new Point(textBoxTelefon.Location.X, textBoxTelefon.Location.Y - 32);
            textBoxNume.Location = new Point(dateTimePickerDataN.Location.X, dateTimePickerDataN.Location.Y - 32);
            textBoxPrenume.Location = new Point(textBoxNume.Location.X, textBoxNume.Location.Y - 32);
            textBoxContinent.Location = new Point(textBoxEmail.Location.X, textBoxEmail.Location.Y + 32);
            textBoxTara.Location = new Point(textBoxContinent.Location.X, textBoxContinent.Location.Y + 32);
            textBoxRegiune.Location = new Point(textBoxTara.Location.X, textBoxTara.Location.Y + 32);
            textBoxLocalitate.Location = new Point(textBoxRegiune.Location.X, textBoxRegiune.Location.Y + 32);
            textBoxSchimbaParola.Location = new Point(textBoxLocalitate.Location.X, textBoxLocalitate.Location.Y + 32);
            buttonEditareCont.Location = new Point(textBoxPrenume.Location.X + 145, textBoxPrenume.Location.Y - 6);
            buttonAnuleaza2.Location = new Point(textBoxPrenume.Location.X + 145, textBoxPrenume.Location.Y - 3);
            buttonSchimbaInformatii.Location = new Point(buttonAnuleaza2.Location.X + 65, buttonAnuleaza2.Location.Y);
            buttonAnuleaza.Location = new Point(textBoxSchimbaParola.Location.X - 60, textBoxSchimbaParola.Location.Y - 2);
            buttonSchimba.Location = new Point(textBoxSchimbaParola.Location.X + 140, textBoxSchimbaParola.Location.Y - 2);
            buttonSchimbaParola.Location = new Point(textBoxSchimbaParola.Location.X + 20, textBoxSchimbaParola.Location.Y + 25);
            buttonStergeCont.Location = new Point(buttonSchimbaParola.Location.X + 3, buttonSchimbaParola.Location.Y + 35);
            labelPrenume.Location = new Point(textBoxPrenume.Location.X - 60, textBoxPrenume.Location.Y + 3);
            labelNume.Location = new Point(textBoxNume.Location.X - 61, textBoxNume.Location.Y + 3);
            labelDataN.Location = new Point(dateTimePickerDataN.Location.X - 69, dateTimePickerDataN.Location.Y + 5);
            labelTelefon.Location = new Point(textBoxTelefon.Location.X - 44, textBoxTelefon.Location.Y + 3);
            labelEmail.Location = new Point(textBoxEmail.Location.X - 35, textBoxEmail.Location.Y + 3);
            labelContinent.Location = new Point(textBoxContinent.Location.X - 57, textBoxContinent.Location.Y + 3);
            labelTara.Location = new Point(textBoxTara.Location.X - 47, textBoxTara.Location.Y + 3);
            labelRegiune.Location = new Point(textBoxRegiune.Location.X - 44, textBoxRegiune.Location.Y + 3);
            labelLocalitate.Location = new Point(textBoxLocalitate.Location.X - 46, textBoxLocalitate.Location.Y + 3);
            buttonAnuleaza.Hide();
            buttonAnuleaza2.Hide();
            buttonSchimba.Hide();
            buttonSchimbaInformatii.Hide();
            buttonEditareCont.Show();
            buttonStergeRezervare.Hide();
            textBoxPretCamera.Hide();
            textBoxCamereRezervare.Hide();
            textBoxDistanta.Hide();
            textBoxPretKm.Hide();
            textBoxPretCamera2.Hide();
            textBoxCamereRezervare2.Hide();
            textBoxDistanta2.Hide();
            textBoxPretKm2.Hide();
            textBoxIdRezervareMaxim2.Hide();
            textBoxPrenume.Enabled = false;
            textBoxNume.Enabled = false;
            textBoxTelefon.Enabled = false;
            textBoxEmail.Enabled = false;
            dateTimePickerDataN.Enabled = false;
            textBoxContinent.Enabled = false;
            textBoxTara.Enabled = false;
            textBoxRegiune.Enabled = false;
            textBoxLocalitate.Enabled = false;
            textBoxSchimbaParola.Enabled = false;
            textBoxPrenume.BackColor = Color.White;
            textBoxNume.BackColor = Color.White;
            textBoxTelefon.BackColor = Color.White;
            textBoxEmail.BackColor = Color.White;
            dateTimePickerDataN.BackColor = Color.White;
            textBoxContinent.BackColor = Color.White;
            textBoxTara.BackColor = Color.White;
            textBoxRegiune.BackColor = Color.White;
            textBoxLocalitate.BackColor = Color.White;
            textBoxSchimbaParola.BackColor = Color.White;
            textBoxTipTransport.Location = new Point(panelVizualizareRezervari.Width / 2 - textBoxTipTransport.Width / 2 + panelVizualizareRezervari.Location.X, panelVizualizareRezervari.Height / 2 - textBoxTipTransport.Height / 2 + panelVizualizareRezervari.Location.Y);
            textBoxNrZile.Location = new Point(textBoxTipTransport.Location.X, textBoxTipTransport.Location.Y - 28);
            dateTimePickerData.Location = new Point(textBoxNrZile.Location.X, textBoxNrZile.Location.Y - 28);
            comboBoxRezervari.Location = new Point(dateTimePickerData.Location.X, dateTimePickerData.Location.Y - 28);
            labelRezervare.Location = new Point(comboBoxRezervari.Location.X - 68, comboBoxRezervari.Location.Y + 3);
            labelData.Location = new Point(dateTimePickerData.Location.X - 34, dateTimePickerData.Location.Y + 3);
            labelNrZile.Location = new Point(textBoxNrZile.Location.X - 35, textBoxNrZile.Location.Y + 3);
            labelTipTransport.Location = new Point(textBoxTipTransport.Location.X - 79, textBoxTipTransport.Location.Y + 3);
            buttonIstoricRezervari.Location = new Point(panelVizualizareRezervari.Width / 2 - buttonIstoricRezervari.Width / 2 + panelVizualizareRezervari.Location.X, buttonIstoricRezervari.Location.Y);
            comboBoxRezervari2.Location = new Point(comboBoxRezervari.Location.X, comboBoxRezervari.Location.Y);
            dateTimePickerData2.Location = new Point(dateTimePickerData.Location.X, dateTimePickerData.Location.Y);
            textBoxNrZile2.Location = new Point(textBoxNrZile.Location.X, textBoxNrZile.Location.Y);
            textBoxTipTransport2.Location = new Point(textBoxTipTransport.Location.X, textBoxTipTransport.Location.Y);
            labelRezervare2.Location = new Point(labelRezervare.Location.X, labelRezervare.Location.Y);
            labelData2.Location = new Point(labelData.Location.X, labelData.Location.Y);
            labelNrZile2.Location = new Point(labelNrZile.Location.X, labelNrZile.Location.Y);
            labelTipTransport2.Location = new Point(labelTipTransport.Location.X, labelTipTransport.Location.Y);
            buttonRezervari2.Location = new Point(buttonDetaliiCont2.Location.X + 100, buttonDetaliiCont2.Location.Y);
        }

        void incarcareDateCont()
        {
            textBoxEmail.Text = textBoxCont.Text;
            con.Open();
            cmd.CommandText = "select prenume from clienti where email='" + textBoxEmail.Text + "'";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    textBoxPrenume.Text = dr[0].ToString();
            con.Close();
            con.Open();
            cmd.CommandText = "select nume from clienti where email='" + textBoxEmail.Text + "'";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    textBoxNume.Text = dr[0].ToString();
            con.Close();
            con.Open();
            cmd.CommandText = "select data_n from clienti where email='" + textBoxEmail.Text + "'";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    dateTimePickerDataN.Text = dr[0].ToString();
            con.Close();
            con.Open();
            cmd.CommandText = "select telefon from clienti where email='" + textBoxEmail.Text + "'";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    textBoxTelefon.Text = dr[0].ToString();
            con.Close();
            con.Open();
            cmd.CommandText = "select continent from continente where id=(select id_continent from clienti where email='" + textBoxEmail.Text + "')";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    textBoxContinent.Text = dr[0].ToString();
            con.Close();
            con.Open();
            cmd.CommandText = "select tara from tari where id=(select id_tara from clienti where email='" + textBoxEmail.Text + "')";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    textBoxTara.Text = dr[0].ToString();
            con.Close();
            con.Open();
            cmd.CommandText = "select regiune from regiuni where id=(select id_regiune from clienti where email='" + textBoxEmail.Text + "')";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    textBoxRegiune.Text = dr[0].ToString();
            con.Close();
            con.Open();
            cmd.CommandText = "select localitate from localitati where id=(select id_localitate from clienti where email='" + textBoxEmail.Text + "')";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    textBoxLocalitate.Text = dr[0].ToString();
            con.Close();
        }

        void incarcareDateRezervare()
        {
            comboBoxRezervari.Items.Clear();
            comboBoxRezervari.Text = "Select a reservation";
            textBoxTipTransport.Text = "";
            textBoxTipBilet.Text = "";
            textBoxNrZile.Text = "";
            textBoxPornire.Text = "";
            textBoxSosire.Text = "";
            labelCamere.Text = "";
            dateTimePickerData.Hide();
            labelData.Hide();
            textBoxNrZile.Hide();
            labelNrZile.Hide();
            textBoxPornire.Hide();
            textBoxSosire.Hide();
            labelPornire.Hide();
            labelSosire.Hide();
            textBoxTipTransport.Hide();
            textBoxTipBilet.Hide();
            labelTipBilet.Hide();
            labelTipTransport.Hide();
            labelCamereAlese.Hide();
            labelCamere.Hide();
            textBoxPretTotal.Hide();
            labelPretTotal.Hide();
            textBoxHotel.Hide();
            labelHotel.Hide();
            con.Open();
            cmd.CommandText = "select id from rezervari where id_client=(select id from clienti where email='" + textBoxCont.Text + "')";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxRezervari.Items.Add(dr[0].ToString());
            con.Close();
            textBoxNrZile.BackColor = Color.White;
            textBoxPornire.BackColor = Color.White;
            textBoxSosire.BackColor = Color.White;
            textBoxTipBilet.BackColor = Color.White;
            textBoxTipTransport.BackColor = Color.White;
            textBoxHotel.BackColor = Color.White; ;
            textBoxPretTotal.BackColor = Color.White;
        }

        private void DetaliiCont_Load(object sender, EventArgs e)
        {
            cmd.Connection = con;
            FormBorderStyle = FormBorderStyle.None;
            WindowState = FormWindowState.Maximized;
            panelDetaliiCont.Dock = DockStyle.Fill;
            panelVizualizareRezervari.Dock = DockStyle.Fill;
            panelIstoricRezervari.Dock = DockStyle.Fill;
            dateTimePickerDataN.Value = DateTime.Today;
            panelDetaliiCont.Show();
            panelVizualizareRezervari.Hide();
            panelIstoricRezervari.Hide();
            textBoxCont.Hide();
            textBoxIdRezervareMaxim.Hide();
            reincarcareRezervari();
            pozitieChenare();
            incarcareDateCont();
        }

        private void buttonStergeCont_Click(object sender, EventArgs e)
        {
            var rez = MessageBox.Show("Are you sure? You won't be able to recover it back and you won't get money back from any unfinished rezervation. Also you will be redirected to the login page.", "Reminder", MessageBoxButtons.YesNo, MessageBoxIcon.Question);
            if (rez == DialogResult.Yes)
            {
                Agentie_de_turism.Properties.Settings.Default.Vizitator = 0;
                Agentie_de_turism.Properties.Settings.Default.Continent = 0;
                Agentie_de_turism.Properties.Settings.Default.Tara = 0;
                con.Open();
                cmd.CommandText = "delete from clienti where email='" + textBoxCont.Text + "'";
                cmd.ExecuteNonQuery();
                con.Close();
                ConectareANDInregistrare CI = new ConectareANDInregistrare();
                CI.ShowDialog();
                this.Close();
            }
        }

        private void buttonInapoi_Click(object sender, EventArgs e)
        {
            k = Agentie_de_turism.Properties.Settings.Default.Vizitator;
            if(k == 1)
            {
                Vizitator viz = new Vizitator(textBoxCont.Text);
                viz.ShowDialog();
                this.Close();
            }
            else
            {
                k = Agentie_de_turism.Properties.Settings.Default.Continent;
                if(k > 0)
                {
                    if(k == 1)
                    {
                        Agentie_de_turism.Properties.Settings.Default.Tara = 0;
                        Europa eu = new Europa(textBoxCont.Text, "Europe");
                        eu.ShowDialog();
                        this.Close();
                    }
                    else
                    {
                        if(k == 2)
                        {
                            Agentie_de_turism.Properties.Settings.Default.Tara = 0;
                            America_de_Nord AmN = new America_de_Nord(textBoxCont.Text, "North America");
                            AmN.ShowDialog();
                            this.Close();
                        }
                        else
                        {
                            if(k == 3)
                            {
                                Agentie_de_turism.Properties.Settings.Default.Tara = 0;
                                America_de_Sud AmS = new America_de_Sud(textBoxCont.Text, "South America");
                                AmS.ShowDialog();
                                this.Close();
                            }
                            else
                            {
                                if(k == 4)
                                {
                                    Agentie_de_turism.Properties.Settings.Default.Tara = 0;
                                    Asia As = new Asia(textBoxCont.Text, "Asia");
                                    As.ShowDialog();
                                    this.Close();
                                }
                                else
                                {
                                    if(k == 5)
                                    {
                                        Agentie_de_turism.Properties.Settings.Default.Tara = 0;
                                        Africa Af = new Africa(textBoxCont.Text, "Africa");
                                        Af.ShowDialog();
                                        this.Close();
                                    }
                                    else
                                    {
                                        if(k == 6)
                                        {
                                            Agentie_de_turism.Properties.Settings.Default.Tara = 0;
                                            Australia Au = new Australia(textBoxCont.Text, "Australia");
                                            Au.ShowDialog();
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

        private void buttonEditareCont_Click(object sender, EventArgs e)
        {
            buttonEditareCont.Hide();
            buttonAnuleaza2.Show();
            buttonSchimbaInformatii.Show();
            buttonSchimbaParola.Enabled = false;
            textBoxTelefon.Enabled = true;
            textBoxEmail.Enabled = true;
            textBoxPrenume.Enabled = true;
            textBoxNume.Enabled = true;
            buttonRezervari.Enabled = false;
        }

        private void buttonAnuleaza2_Click(object sender, EventArgs e)
        {
            buttonAnuleaza2.Hide();
            buttonSchimbaInformatii.Hide();
            buttonEditareCont.Show();
            buttonRezervari.Enabled = true;
            buttonSchimbaParola.Enabled = true;
            textBoxTelefon.Enabled = false;
            textBoxEmail.Enabled = false;
            textBoxPrenume.Enabled = false;
            textBoxNume.Enabled = false;
            textBoxPrenume.BackColor = Color.White;
            textBoxNume.BackColor = Color.White;
            textBoxTelefon.BackColor = Color.White;
            textBoxEmail.BackColor = Color.White;
            incarcareDateCont();
        }

        int verificareEmail()
        {
            con.Open();
            cmd.CommandText = "select email from clienti where email='" + textBoxEmail.Text + "'";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
            {
                con.Close();
                return 0;
            }
            con.Close();
            con.Open();
            cmd.CommandText = "select email from hoteluri where email='" + textBoxEmail.Text + "'";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
            {
                con.Close();
                return 0;
            }
            con.Close();
            return 1;
        }

        private void buttonSchimbaInformatii_Click(object sender, EventArgs e)
        {
            int d = verificareEmail();
            bool c = Regex.IsMatch(textBoxEmail.Text, @"^[^@\s]+@[^@\s]+\.[^@\s]+$");
            if(d == 0)
                MessageBox.Show("The email already exists!", "Error!!!", MessageBoxButtons.OK, MessageBoxIcon.Error);
            else
                if(!c)
                    MessageBox.Show("Invalid email format!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                else
                {
                    con.Open();
                    cmd.CommandText = "update clienti set nume='" + textBoxNume.Text + "', prenume='" + textBoxPrenume.Text + "', telefon='" + textBoxTelefon.Text + "', email='" + textBoxEmail.Text + "' where email='" + textBoxCont.Text + "'";
                    cmd.ExecuteNonQuery();
                    con.Close();
                    textBoxCont.Text = textBoxEmail.Text;
                    buttonAnuleaza2.Hide();
                    buttonSchimbaInformatii.Hide();
                    buttonEditareCont.Show();
                    buttonSchimbaParola.Enabled = true;
                    textBoxTelefon.Enabled = false;
                    textBoxEmail.Enabled = false;
                    textBoxPrenume.Enabled = false;
                    textBoxNume.Enabled = false;
                    buttonRezervari.Enabled = true;
                    textBoxPrenume.BackColor = Color.White;
                    textBoxNume.BackColor = Color.White;
                    textBoxTelefon.BackColor = Color.White;
                    textBoxEmail.BackColor = Color.White;
                    incarcareDateCont();
                }
        }

        private void buttonSchimbaParola_Click(object sender, EventArgs e)
        {
            buttonSchimbaParola.Hide();
            buttonAnuleaza.Show();
            buttonSchimba.Show();
            buttonRezervari.Enabled = false;
            buttonEditareCont.Enabled = false;
            textBoxSchimbaParola.Enabled = true;
        }

        private void buttonAnuleaza_Click(object sender, EventArgs e)
        {
            buttonRezervari.Enabled = true;
            textBoxSchimbaParola.Text = "";
            buttonEditareCont.Enabled = true;
            buttonAnuleaza.Hide();
            buttonSchimba.Hide();
            buttonSchimbaParola.Show();
            textBoxSchimbaParola.Enabled = false;
            textBoxSchimbaParola.BackColor = Color.White;
        }

        private void buttonSchimba_Click(object sender, EventArgs e)
        {
            var rez = MessageBox.Show("Are you sure? This will be your new password and you will be redirected to the login page in order to update it.", "Reminder", MessageBoxButtons.YesNo, MessageBoxIcon.Question);
            if (rez == DialogResult.Yes)
            {
                buttonRezervari.Enabled = true;
                buttonEditareCont.Enabled = true;
                con.Open();
                cmd.CommandText = "update clienti set parola='" + textBoxSchimbaParola.Text + "' where email='" + textBoxCont.Text + "'";
                cmd.ExecuteNonQuery();
                con.Close();
                ConectareANDInregistrare CI = new ConectareANDInregistrare();
                CI.ShowDialog();
                this.Close();
            }
        }

        private void textBoxTelefon_TextChanged(object sender, EventArgs e)
        {
            if (textBoxTelefon.Text.StartsWith(" "))
            {
                MessageBox.Show("Space can't be the first character!", "Error!!!", MessageBoxButtons.OK, MessageBoxIcon.Error);
                textBoxTelefon.Clear();
            }
        }

        void afisarePozitieCasuteIstoric()
        {
            if (textBoxTipTransport2.Text == "Airplane")
            {
                con.Open();
                cmd.CommandText = "select tip_bilet from istoricerezervari where id='" + comboBoxRezervari2.Text + "'";
                dr = cmd.ExecuteReader();
                if (dr.HasRows)
                    while (dr.Read())
                        textBoxTipBilet2.Text = dr[0].ToString();
                con.Close();
                con.Open();
                cmd.CommandText = "select (select localitate from localitati where id=id_loc1) from distante where id=(select id_distanta from istoricerezervari where id='" + comboBoxRezervari2.Text + "')";
                dr = cmd.ExecuteReader();
                if (dr.HasRows)
                    while (dr.Read())
                        textBoxPornire2.Text = dr[0].ToString();
                con.Close();
                con.Open();
                cmd.CommandText = "select (select localitate from localitati where id=id_loc2) from distante where id=(select id_distanta from istoricerezervari where id='" + comboBoxRezervari2.Text + "')";
                dr = cmd.ExecuteReader();
                if (dr.HasRows)
                    while (dr.Read())
                        textBoxSosire2.Text = dr[0].ToString();
                con.Close();
                con.Open();
                cmd.CommandText = "select distanta from distante where id=(select id_distanta from istoricerezervari where id='" + comboBoxRezervari2.Text + "')";
                dr = cmd.ExecuteReader();
                if (dr.HasRows)
                    while (dr.Read())
                        textBoxDistanta2.Text = dr[0].ToString();
                con.Close();
                con.Open();
                cmd.CommandText = "select pret_km from tip_transporturi where tip='" + textBoxTipTransport2.Text + "'";
                dr = cmd.ExecuteReader();
                if (dr.HasRows)
                    while (dr.Read())
                        textBoxPretKm2.Text = dr[0].ToString();
                con.Close();
                if (textBoxTipBilet2.Text == "Round trip ticket")
                    pretTotal += float.Parse(textBoxDistanta2.Text) * float.Parse(textBoxPretKm2.Text) * 2;
                else
                    pretTotal += float.Parse(textBoxDistanta2.Text) * float.Parse(textBoxPretKm2.Text);
                textBoxTipBilet2.Location = new Point(textBoxTipTransport2.Location.X, textBoxTipTransport2.Location.Y + 28);
                labelTipBilet2.Location = new Point(textBoxTipBilet2.Location.X - 64, textBoxTipBilet2.Location.Y + 3);
                textBoxPornire2.Location = new Point(textBoxTipBilet2.Location.X - 80, textBoxTipBilet2.Location.Y + 28);
                textBoxSosire2.Location = new Point(textBoxPornire2.Location.X + 170, textBoxPornire2.Location.Y);
                labelPornire2.Location = new Point(textBoxPornire2.Location.X - 35, textBoxPornire2.Location.Y + 3);
                labelSosire2.Location = new Point(textBoxSosire2.Location.X - 24, textBoxSosire2.Location.Y + 3);
                textBoxHotel2.Location = new Point(textBoxTipBilet2.Location.X, textBoxTipBilet2.Location.Y + 63);
                labelHotel2.Location = new Point(textBoxHotel2.Location.X - 37, textBoxHotel2.Location.Y + 3);
                labelCamere2.Location = new Point(textBoxHotel2.Location.X, textBoxHotel2.Location.Y + 28);
                labelCamereAlese2.Location = new Point(labelCamere2.Location.X - 45, labelCamere2.Location.Y + 3);
                textBoxNrZile2.Show();
                labelNrZile2.Show();
                textBoxPornire2.Show();
                textBoxSosire2.Show();
                labelPornire2.Show();
                labelSosire2.Show();
                textBoxTipTransport2.Show();
                textBoxTipBilet2.Show();
                labelTipBilet2.Show();
                labelTipTransport2.Show();
                labelCamereAlese2.Show();
                labelCamere2.Show();
                dateTimePickerData2.Show();
                labelData2.Show();

            }
            else
                if (textBoxTipTransport2.Text == "By myself")
            {
                textBoxHotel2.Location = new Point(textBoxTipTransport2.Location.X, textBoxTipTransport2.Location.Y);
                labelHotel2.Location = new Point(textBoxHotel2.Location.X - 37, textBoxHotel2.Location.Y + 3);
                labelCamere2.Location = new Point(textBoxHotel2.Location.X, textBoxHotel2.Location.Y + 28);
                labelCamereAlese2.Location = new Point(labelCamere2.Location.X - 45, labelCamere2.Location.Y);
                textBoxNrZile2.Show();
                labelNrZile2.Show();
                textBoxPornire2.Hide();
                textBoxSosire2.Hide();
                labelPornire2.Hide();
                labelSosire2.Hide();
                textBoxTipTransport2.Hide();
                textBoxTipBilet2.Hide();
                labelTipBilet2.Hide();
                labelTipTransport2.Hide();
                labelCamereAlese2.Show();
                labelCamere2.Show();
                dateTimePickerData2.Show();
                labelData2.Show();
            }
        }

        private void comboBoxRezervari2_SelectedIndexChanged(object sender, EventArgs e)
        {
            yPret = 0;
            pretTotal = 0;
            camereRezervare = null;
            camereRezervare2 = null;
            con.Open();
            textBoxCamereRezervare.Text = "";
            labelCamere.Text = "";
            cmd.CommandText = "select data from istoricerezervari where id='" + comboBoxRezervari2.Text + "'";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    dateTimePickerData2.Value = Convert.ToDateTime(dr[0].ToString());
            con.Close();
            con.Open();
            cmd.CommandText = "select nr_zile from istoricerezervari where id_client=(select id from clienti where email='" + textBoxCont.Text + "') and id='" + comboBoxRezervari2.Text + "'";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    textBoxNrZile2.Text = dr[0].ToString();
            con.Close();
            con.Open();
            cmd.CommandText = "select tip from tip_transporturi where id=(select id_tip_transport from istoricerezervari where id='" + comboBoxRezervari2.Text + "')";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    textBoxTipTransport2.Text = dr[0].ToString();
            con.Close();
            con.Open();
            cmd.CommandText = "select denumire from hoteluri where id=(select id_hotel from istoricerezervari where id='" + comboBoxRezervari2.Text + "')";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    textBoxHotel2.Text = dr[0].ToString();
            con.Close();
            con.Open();
            cmd.CommandText = "select id_camera from istoricerezervari where id='" + comboBoxRezervari2.Text + "'";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    textBoxCamereRezervare2.Text = dr[0].ToString();
            con.Close();
            camereRezervare = textBoxCamereRezervare2.Text.Split('_');
            textBoxCamereRezervare.Text = "";
            for (int i = 0; i < camereRezervare.Length; i++)
            {
                con.Open();
                cmd.CommandText = "select tip from tip_camere where id=(select id_tip_camera from camere where id='" + camereRezervare[i] + "')";
                dr = cmd.ExecuteReader();
                if (dr.HasRows)
                    while (dr.Read())
                        textBoxCamereRezervare2.Text += dr[0].ToString() + "_";
                con.Close();
            }
            camereRezervare = null;
            camereRezervare = textBoxCamereRezervare2.Text.Split('_');
            camereRezervare2 = textBoxCamereRezervare2.Text.Split('_');
            for (int i = 0; i < camereRezervare.Length - 1; i++)
            {
                c = 0;
                for (int j = 0; j < camereRezervare2.Length - 1; j++)
                {
                    if (camereRezervare2[j] != "0")
                        if (camereRezervare[i] == camereRezervare2[j])
                        {
                            c++;
                            camereRezervare2[j] = "0";
                        }
                }
                if (c > 0)
                {
                    con.Open();
                    cmd.CommandText = "select pret from tip_camere where tip='" + camereRezervare[i] + "'";
                    dr = cmd.ExecuteReader();
                    if (dr.HasRows)
                        while (dr.Read())
                            textBoxPretCamera2.Text = dr[0].ToString();
                    con.Close();
                    labelCamere2.Text += camereRezervare[i] + " - " + textBoxPretCamera2.Text + "(€)/night" + " -> " + c.ToString() + "\n";
                    yPret++;
                    pretTotal += float.Parse(textBoxPretCamera2.Text) * c;
                }
            }
            if (int.Parse(textBoxNrZile2.Text) > 1)
                pretTotal = pretTotal * (int.Parse(textBoxNrZile2.Text) - 1);
            afisarePozitieCasuteIstoric();
            textBoxPretTotal2.Text = pretTotal.ToString();
            textBoxPretTotal2.Location = new Point(labelCamere2.Location.X, labelCamere2.Location.Y + (20 - 5 * (yPret - 1)) * yPret);
            labelPretTotal2.Location = new Point(textBoxPretTotal2.Location.X - 60, textBoxPretTotal2.Location.Y + 3);
            textBoxPretTotal2.Show();
            labelPretTotal2.Show();
            textBoxHotel2.Show();
            labelHotel2.Show();
        }

        void incarcareIstoricRezervare()
        {
            comboBoxRezervari2.Items.Clear();
            comboBoxRezervari2.Text = "Select a reservation";
            textBoxTipTransport2.Text = "";
            textBoxTipBilet2.Text = "";
            textBoxNrZile2.Text = "";
            textBoxPornire2.Text = "";
            textBoxSosire2.Text = "";
            labelCamere2.Text = "";
            dateTimePickerData2.Hide();
            labelData2.Hide();
            textBoxNrZile2.Hide();
            labelNrZile2.Hide();
            textBoxPornire2.Hide();
            textBoxSosire2.Hide();
            labelPornire2.Hide();
            labelSosire2.Hide();
            textBoxTipTransport2.Hide();
            textBoxTipBilet2.Hide();
            labelTipBilet2.Hide();
            labelTipTransport2.Hide();
            labelCamereAlese2.Hide();
            labelCamere2.Hide();
            textBoxPretTotal2.Hide();
            labelPretTotal2.Hide();
            textBoxHotel2.Hide();
            labelHotel2.Hide();
            con.Open();
            cmd.CommandText = "select id from istoricerezervari where id_client=(select id from clienti where email='" + textBoxCont.Text + "')";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxRezervari2.Items.Add(dr[0].ToString());
            con.Close();
            textBoxNrZile2.BackColor = Color.White;
            textBoxPornire2.BackColor = Color.White;
            textBoxSosire2.BackColor = Color.White;
            textBoxTipBilet2.BackColor = Color.White;
            textBoxTipTransport2.BackColor = Color.White;
            textBoxHotel2.BackColor = Color.White; ;
            textBoxPretTotal2.BackColor = Color.White;
        }

        private void buttonIstoricRezervari_Click(object sender, EventArgs e)
        {
            panelVizualizareRezervari.Hide();
            panelDetaliiCont.Hide();
            incarcareIstoricRezervare();
            panelIstoricRezervari.Show();
        }

        private void buttonDetaliiCont2_Click(object sender, EventArgs e)
        {
            incarcareIstoricRezervare();
            panelIstoricRezervari.Hide();
            panelVizualizareRezervari.Hide();
            panelDetaliiCont.Show();
        }

        private void buttonRezervari2_Click(object sender, EventArgs e)
        {
            panelIstoricRezervari.Hide();
            panelDetaliiCont.Hide();
            incarcareDateRezervare();
            incarcareIstoricRezervare();
            panelVizualizareRezervari.Show();
        }

        private void textBoxTelefon_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsDigit(e.KeyChar) && !char.IsControl(e.KeyChar))
            {
                e.Handled = true;
                MessageBox.Show("Insert only numbers in this field!", "Error!!!", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            else
                e.Handled = false;
        }

        private void textBoxEmail_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (char.IsWhiteSpace(e.KeyChar))
            {
                e.Handled = true;
                MessageBox.Show("Space is not allowed in this field!", "Error!!!", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            else
                e.Handled = false;
        }

        private void buttonRezervari_Click(object sender, EventArgs e)
        {
            panelIstoricRezervari.Hide();
            panelDetaliiCont.Hide();
            incarcareDateRezervare();
            panelVizualizareRezervari.Show();
        }

        private void comboBoxRezervari_KeyPress(object sender, KeyPressEventArgs e)
        {
            e.Handled = true;
        }

        private void buttonStergeRezervare_Click(object sender, EventArgs e)
        {
            buttonStergeRezervare.Hide();
            stergeRezervare();
            incarcareDateRezervare();
        }

        private void buttonDetaliiCont_Click(object sender, EventArgs e)
        {
            incarcareDateRezervare();
            panelIstoricRezervari.Hide();
            panelVizualizareRezervari.Hide();
            panelDetaliiCont.Show();
        }

        void afisarePozitieCasuteRezervare()
        {
            if(textBoxTipTransport.Text == "Airplane")
            {
                con.Open();
                cmd.CommandText = "select tip_bilet from rezervari where id='" + comboBoxRezervari.Text + "'";
                dr = cmd.ExecuteReader();
                if (dr.HasRows)
                    while (dr.Read())
                        textBoxTipBilet.Text = dr[0].ToString();
                con.Close();
                con.Open();
                cmd.CommandText = "select (select localitate from localitati where id=id_loc1) from distante where id=(select id_distanta from rezervari where id='" + comboBoxRezervari.Text + "')";
                dr = cmd.ExecuteReader();
                if (dr.HasRows)
                    while (dr.Read())
                        textBoxPornire.Text = dr[0].ToString();
                con.Close();
                con.Open();
                cmd.CommandText = "select (select localitate from localitati where id=id_loc2) from distante where id=(select id_distanta from rezervari where id='" + comboBoxRezervari.Text + "')";
                dr = cmd.ExecuteReader();
                if (dr.HasRows)
                    while (dr.Read())
                        textBoxSosire.Text = dr[0].ToString();
                con.Close();
                con.Open();
                cmd.CommandText = "select distanta from distante where id=(select id_distanta from rezervari where id='" + comboBoxRezervari.Text + "')";
                dr = cmd.ExecuteReader();
                if (dr.HasRows)
                    while (dr.Read())
                        textBoxDistanta.Text = dr[0].ToString();
                con.Close();
                con.Open();
                cmd.CommandText = "select pret_km from tip_transporturi where tip='" + textBoxTipTransport.Text + "'";
                dr = cmd.ExecuteReader();
                if (dr.HasRows)
                    while (dr.Read())
                        textBoxPretKm.Text = dr[0].ToString();
                con.Close();
                if (textBoxTipBilet.Text == "Round trip ticket")
                    pretTotal += float.Parse(textBoxDistanta.Text) * float.Parse(textBoxPretKm.Text) * 2;
                else
                    pretTotal += float.Parse(textBoxDistanta.Text) * float.Parse(textBoxPretKm.Text);
                textBoxTipBilet.Location = new Point(textBoxTipTransport.Location.X, textBoxTipTransport.Location.Y + 28);
                labelTipBilet.Location = new Point(textBoxTipBilet.Location.X - 64, textBoxTipBilet.Location.Y + 3);
                textBoxPornire.Location = new Point(textBoxTipBilet.Location.X - 80, textBoxTipBilet.Location.Y + 28);
                textBoxSosire.Location = new Point(textBoxPornire.Location.X + 170, textBoxPornire.Location.Y);
                labelPornire.Location = new Point(textBoxPornire.Location.X - 35, textBoxPornire.Location.Y + 3);
                labelSosire.Location = new Point(textBoxSosire.Location.X - 24, textBoxSosire.Location.Y + 3);
                textBoxHotel.Location = new Point(textBoxTipBilet.Location.X, textBoxTipBilet.Location.Y + 63);
                labelHotel.Location = new Point(textBoxHotel.Location.X - 37, textBoxHotel.Location.Y + 3);
                labelCamere.Location = new Point(textBoxHotel.Location.X, textBoxHotel.Location.Y + 28);
                labelCamereAlese.Location = new Point(labelCamere.Location.X - 45, labelCamere.Location.Y + 3);
                textBoxNrZile.Show();
                labelNrZile.Show();
                textBoxPornire.Show();
                textBoxSosire.Show();
                labelPornire.Show();
                labelSosire.Show();
                textBoxTipTransport.Show();
                textBoxTipBilet.Show();
                labelTipBilet.Show();
                labelTipTransport.Show();
                labelCamereAlese.Show();
                labelCamere.Show();
                dateTimePickerData.Show();
                labelData.Show();
            }
            else
                if(textBoxTipTransport.Text == "By myself")
                {
                    textBoxHotel.Location = new Point(textBoxTipTransport.Location.X, textBoxTipTransport.Location.Y);
                    labelHotel.Location = new Point(textBoxHotel.Location.X - 37, textBoxHotel.Location.Y + 3);
                    labelCamere.Location = new Point(textBoxHotel.Location.X, textBoxHotel.Location.Y + 28);
                    labelCamereAlese.Location = new Point(labelCamere.Location.X - 45, labelCamere.Location.Y);
                    textBoxNrZile.Show();
                    labelNrZile.Show();
                    textBoxPornire.Hide();
                    textBoxSosire.Hide();
                    labelPornire.Hide();
                    labelSosire.Hide();
                    textBoxTipTransport.Hide();
                    textBoxTipBilet.Hide();
                    labelTipBilet.Hide();
                    labelTipTransport.Hide();
                    labelCamereAlese.Show();
                    labelCamere.Show();
                    dateTimePickerData.Show();
                    labelData.Show();
                }
        }

        private void comboBoxRezervari_SelectedIndexChanged(object sender, EventArgs e)
        {
            yPret = 0;
            pretTotal = 0;
            camereRezervare = null;
            camereRezervare2 = null;
            con.Open();
            textBoxCamereRezervare.Text = "";
            labelCamere.Text = "";
            cmd.CommandText = "select data from rezervari where id='" + comboBoxRezervari.Text + "'";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    dateTimePickerData.Value = Convert.ToDateTime(dr[0].ToString());
            con.Close();
            con.Open();
            cmd.CommandText = "select nr_zile from rezervari where id_client=(select id from clienti where email='" + textBoxCont.Text + "') and id='" + comboBoxRezervari.Text + "'";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    textBoxNrZile.Text = dr[0].ToString();
            con.Close();
            con.Open();
            cmd.CommandText = "select tip from tip_transporturi where id=(select id_tip_transport from rezervari where id='" + comboBoxRezervari.Text + "')";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    textBoxTipTransport.Text = dr[0].ToString();
            con.Close();
            con.Open();
            cmd.CommandText = "select denumire from hoteluri where id=(select id_hotel from rezervari where id='" + comboBoxRezervari.Text + "')";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    textBoxHotel.Text = dr[0].ToString();
            con.Close();
            con.Open();
            cmd.CommandText = "select id_camera from rezervari where id='" + comboBoxRezervari.Text + "'";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    textBoxCamereRezervare.Text = dr[0].ToString();
            con.Close();
            camereRezervare = textBoxCamereRezervare.Text.Split('_');
            textBoxCamereRezervare.Text = "";
            for(int i = 0; i < camereRezervare.Length; i++)
            {
                con.Open();
                cmd.CommandText = "select tip from tip_camere where id=(select id_tip_camera from camere where id='" + camereRezervare[i] + "')";
                dr = cmd.ExecuteReader();
                if (dr.HasRows)
                    while (dr.Read())
                        textBoxCamereRezervare.Text += dr[0].ToString() + "_";
                con.Close();
            }
            camereRezervare = null;
            camereRezervare = textBoxCamereRezervare.Text.Split('_');
            camereRezervare2 = textBoxCamereRezervare.Text.Split('_');
            for(int i = 0; i < camereRezervare.Length - 1; i++)
            {
                c = 0;
                for(int j = 0; j < camereRezervare2.Length - 1; j++)
                {
                    if (camereRezervare2[j] != "0")
                        if (camereRezervare[i] == camereRezervare2[j])
                        {
                            c++;
                            camereRezervare2[j] = "0";
                        }
                }
                if(c > 0)
                {
                    con.Open();
                    cmd.CommandText = "select pret from tip_camere where tip='" + camereRezervare[i] + "'";
                    dr = cmd.ExecuteReader();
                    if (dr.HasRows)
                        while (dr.Read())
                            textBoxPretCamera.Text = dr[0].ToString();
                    con.Close();
                    labelCamere.Text += camereRezervare[i] + " - " + textBoxPretCamera.Text + "(€)/night" + " -> " + c.ToString() + "\n";
                    yPret++;
                    pretTotal += float.Parse(textBoxPretCamera.Text) * c;
                }
            }
            if (int.Parse(textBoxNrZile.Text) > 1)
                pretTotal = pretTotal * (int.Parse(textBoxNrZile.Text) - 1);
            afisarePozitieCasuteRezervare();
            textBoxPretTotal.Text = pretTotal.ToString();
            textBoxPretTotal.Location = new Point(labelCamere.Location.X, labelCamere.Location.Y + (20-5*(yPret-1))*yPret);
            labelPretTotal.Location = new Point(textBoxPretTotal.Location.X - 60, textBoxPretTotal.Location.Y + 3);
            textBoxPretTotal.Show();
            labelPretTotal.Show();
            textBoxHotel.Show();
            labelHotel.Show();
            buttonStergeRezervare.Show();
        }
    }
}
