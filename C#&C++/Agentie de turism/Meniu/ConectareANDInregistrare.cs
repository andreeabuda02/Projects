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
    public partial class ConectareANDInregistrare : Form
    {
        SqlConnection con = new SqlConnection(@"Data Source=(LocalDB)\MSSQLLocalDB;AttachDbFilename=D:\CSharp\Agentie de turism\Agentie de turism\Database1.mdf;Integrated Security=True");
        SqlCommand cmd = new SqlCommand();
        SqlDataReader dr;

        int k,schimba;

        public ConectareANDInregistrare()
        {
            InitializeComponent();
        }

        private void ConectareANDInregistrare_Load(object sender, EventArgs e)
        {
            cmd.Connection = con;
            FormBorderStyle = FormBorderStyle.None;
            WindowState = FormWindowState.Maximized;
            panelLogare.Dock = DockStyle.Fill;
            panelCreareCont.Dock = DockStyle.Fill;
            panelLogare.Show();
            panelCreareCont.Hide();
            dateTimePickerDataN.Value = DateTime.Today;
            checkBoxRetinere.Location = new Point(((panelLogare.Width - checkBoxRetinere.Width) / 2), ((panelLogare.Height - checkBoxRetinere.Height) / 2));
            buttonLogare.Location = new Point((checkBoxRetinere.Location.X + 45), (checkBoxRetinere.Location.Y + 38));
            buttonInregistrare.Location = new Point((buttonLogare.Location.X - 100), (buttonLogare.Location.Y));
            textBoxParola.Location = new Point((checkBoxRetinere.Location.X),(checkBoxRetinere.Location.Y - 26));
            textBoxEmail.Location = new Point((textBoxParola.Location.X),(textBoxParola.Location.Y - 26));
            label1.Location = new Point((textBoxEmail.Location.X - 60), (textBoxEmail.Location.Y));
            label2.Location = new Point((textBoxParola.Location.X - 84), (textBoxParola.Location.Y));
            comboBoxTara.Location = new Point(((panelCreareCont.Width - comboBoxTara.Width) / 2), ((panelCreareCont.Height - buttonCreareCont.Height) / 2));
            comboBoxRegiune.Location = new Point((comboBoxTara.Location.X), (comboBoxTara.Location.Y + 27));
            comboBoxLocalitate.Location = new Point((comboBoxRegiune.Location.X), (comboBoxRegiune.Location.Y + 27));
            textBoxParolaNoua.Location = new Point((comboBoxLocalitate.Location.X), (comboBoxLocalitate.Location.Y + 27));
            textBoxParolaVerificare.Location = new Point((textBoxParolaNoua.Location.X), (textBoxParolaNoua.Location.Y + 27));
            labelParola.Location = new Point((textBoxParolaVerificare.Location.X - 3), (textBoxParolaVerificare.Location.Y + 23));
            buttonCreareCont.Location = new Point((labelParola.Location.X + 48), (labelParola.Location.Y + 19));
            comboBoxContinent.Location = new Point((comboBoxTara.Location.X), (comboBoxTara.Location.Y - 27));
            textBoxEmail2.Location = new Point((comboBoxContinent.Location.X), (comboBoxContinent.Location.Y - 27));
            textBoxTelefon.Location = new Point((textBoxEmail2.Location.X), (textBoxEmail2.Location.Y - 27));
            dateTimePickerDataN.Location = new Point((textBoxTelefon.Location.X), (textBoxTelefon.Location.Y - 27));
            textBoxNume.Location = new Point((dateTimePickerDataN.Location.X), (dateTimePickerDataN.Location.Y - 27));
            textBoxPrenume.Location = new Point((textBoxNume.Location.X), (textBoxNume.Location.Y - 27));
            label9.Location = new Point((comboBoxTara.Location.X - 75), (comboBoxTara.Location.Y));
            label10.Location = new Point((comboBoxRegiune.Location.X - 65), (comboBoxRegiune.Location.Y));
            label11.Location = new Point((comboBoxLocalitate.Location.X -75), (comboBoxLocalitate.Location.Y));
            label12.Location = new Point((textBoxParolaNoua.Location.X - 84), (textBoxParolaNoua.Location.Y));
            label13.Location = new Point((textBoxParolaVerificare.Location.X - 143), (textBoxParolaVerificare.Location.Y));
            label8.Location = new Point((comboBoxContinent.Location.X - 87), (comboBoxContinent.Location.Y));
            label7.Location = new Point((textBoxEmail2.Location.X - 60), (textBoxEmail2.Location.Y));
            label6.Location = new Point((textBoxTelefon.Location.X - 61), (textBoxTelefon.Location.Y));
            label5.Location = new Point((dateTimePickerDataN.Location.X - 90), (dateTimePickerDataN.Location.Y));
            label4.Location = new Point((textBoxNume.Location.X - 92), (textBoxNume.Location.Y));
            label3.Location = new Point((textBoxPrenume.Location.X - 96), (textBoxPrenume.Location.Y));
            panelLogare.BackgroundImage = Agentie_de_turism.Properties.Resources.Logare;
            panelCreareCont.BackgroundImage = Agentie_de_turism.Properties.Resources.CreareCont;
            textBoxVerificareParola.Hide();
            k = Agentie_de_turism.Properties.Settings.Default.retineCont;
            schimba = 0;
            if(k == 1)
            {
                textBoxEmail.Text = Agentie_de_turism.Properties.Settings.Default.email;
                textBoxParola.Text = Agentie_de_turism.Properties.Settings.Default.parola;
                checkBoxRetinere.Checked = true;
            }
            else
            {
                textBoxEmail.Text = "";
                textBoxParola.Text = "";
                checkBoxRetinere.Checked = false;
            }
            schimba = 1;
        }

        void incarcareContinente()
        {
            comboBoxContinent.Items.Clear();
            con.Open();
            cmd.CommandText = "select continent from continente";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxContinent.Items.Add(dr[0].ToString());
            con.Close();
        }

        void incarcareTari()
        {
            comboBoxTara.Items.Clear();
            con.Open();
            cmd.CommandText = "select tara from tari where id_continent=(select id from continente where continent='" + comboBoxContinent.Text + "')";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxTara.Items.Add(dr[0].ToString());
            con.Close();
        }
        void incarcareRegiune()
        {
            comboBoxRegiune.Items.Clear();
            con.Open();
            cmd.CommandText = "select regiune from regiuni where id_tara=(select id from tari where tara='" + comboBoxTara.Text + "')";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxRegiune.Items.Add(dr[0].ToString());
            con.Close();
        }
        void incarcareLocalitati()
        {
            comboBoxLocalitate.Items.Clear();
            con.Open();
            cmd.CommandText = "select localitate from localitati where id_regiune=(select id from regiuni where regiune='" + comboBoxRegiune.Text + "' and id_tara=(select id from tari where tara='" + comboBoxTara.Text + "'))";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
                while (dr.Read())
                    comboBoxLocalitate.Items.Add(dr[0].ToString());
            con.Close();
        }

        void stergereCasute()
        {
            textBoxPrenume.Text = "";
            textBoxNume.Text = "";
            textBoxEmail2.Text = "";
            textBoxTelefon.Text = "";
            dateTimePickerDataN.Value = DateTime.Today;
            incarcareContinente();
            incarcareTari();
            incarcareRegiune();
            incarcareLocalitati();
            comboBoxContinent.Text = "";
            comboBoxTara.Text = "";
            comboBoxRegiune.Text = "";
            comboBoxLocalitate.Text = "";
            textBoxParolaNoua.Text = "";
            textBoxParolaVerificare.Text = "";
        }

        private void buttonInapoi_Click(object sender, EventArgs e)
        {
            Form1 f = new Form1();
            this.Close();
            f.ShowDialog();

        }

        private void buttonInapoi2_Click(object sender, EventArgs e)
        {
            panelCreareCont.Hide();
            panelLogare.Show();
            stergereCasute();
        }

        private void buttonInregistrare_Click(object sender, EventArgs e)
        {
            textBoxEmail.Text = "";
            textBoxParola.Text = "";
            panelLogare.Hide();
            panelCreareCont.Show();
            labelParola.Hide();
            incarcareContinente();
        }

        private void buttonLogare_Click_1(object sender, EventArgs e)
        {
            if(textBoxEmail.Text != "" && textBoxParola.Text != "")
            {
                con.Open();
                cmd.CommandText = "select parola from clienti where email='" + textBoxEmail.Text + "'";
                dr = cmd.ExecuteReader();
                if (dr.HasRows)
                    while (dr.Read())
                        textBoxVerificareParola.Text = dr[0].ToString();
                con.Close();
                if (textBoxParola.Text == textBoxVerificareParola.Text)
                {
                    if(k == 1)
                    {
                        Agentie_de_turism.Properties.Settings.Default.email = textBoxEmail.Text;
                        Agentie_de_turism.Properties.Settings.Default.parola = textBoxParola.Text;
                    }
                    else
                    {
                        Agentie_de_turism.Properties.Settings.Default.email = "";
                        Agentie_de_turism.Properties.Settings.Default.parola = "";
                    }
                    Vizitator viz = new Vizitator(textBoxEmail.Text);
                    this.Close();
                    viz.ShowDialog();
                }
                else
                {
                    MessageBox.Show("One of the following fields are wrong: email, password!", "Error!!!", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    con.Close();
                }
            }
            else
                if(textBoxEmail.Text == "" && textBoxParola.Text == "")
                    MessageBox.Show("Complete all the fields in order to login!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                else
                    if(textBoxEmail.Text == "")
                        MessageBox.Show("Add an email in the email field!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                    else
                        if(textBoxParola.Text == "")
                            MessageBox.Show("Add a password in the field password!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
        }

        private void comboBoxContinent_SelectedIndexChanged(object sender, EventArgs e)
        {
            comboBoxTara.Items.Clear();
            comboBoxRegiune.Items.Clear();
            comboBoxLocalitate.Items.Clear();
            comboBoxTara.Text = "";
            comboBoxRegiune.Text = "";
            comboBoxLocalitate.Text = "";
            incarcareTari();
        }

        private void comboBoxTara_SelectedIndexChanged(object sender, EventArgs e)
        {
            comboBoxRegiune.Items.Clear();
            comboBoxLocalitate.Items.Clear();
            comboBoxRegiune.Text = "";
            comboBoxLocalitate.Text = "";
            incarcareRegiune();
        }

        private void comboBoxRegiune_SelectedIndexChanged(object sender, EventArgs e)
        {
            comboBoxLocalitate.Items.Clear();
            comboBoxLocalitate.Text = "";
            incarcareLocalitati();
        }

        int verificareEmail()
        {
            con.Open();
            cmd.CommandText = "select email from clienti where email='" + textBoxEmail2.Text + "'";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
            {
                con.Close();
                return 0;
            }
            con.Close();
            con.Open();
            cmd.CommandText = "select email from hoteluri where email='" + textBoxEmail2.Text + "'";
            dr = cmd.ExecuteReader();
            if (dr.HasRows)
            {
                con.Close();
                return 0;
            }
            con.Close();
            return 1;
        }

        private void buttonCreareCont_Click(object sender, EventArgs e)
        {
            int d = verificareEmail(), an = (DateTime.Now.Subtract(dateTimePickerDataN.Value).Days) / (365);
            bool c = Regex.IsMatch(textBoxEmail2.Text, @"^[^@\s]+@[^@\s]+\.[^@\s]+$");
            if (textBoxNume.Text != "" && textBoxPrenume.Text != "" && textBoxTelefon.Text != "" && textBoxEmail2.Text != "" && textBoxParolaNoua.Text != "" && textBoxParolaVerificare.Text != "" && comboBoxContinent.Text != "" && comboBoxTara.Text != "" && comboBoxRegiune.Text != "" && comboBoxLocalitate.Text != "" && textBoxParolaNoua.Text == textBoxParolaVerificare.Text && c && d == 1 && an > 17)
            {
                con.Open();
                cmd.CommandText = "insert into clienti(nume,prenume,data_n,telefon,email,id_localitate,id_regiune,id_tara,id_continent,parola) values('" + textBoxNume.Text + "','" + textBoxPrenume.Text + "','" + dateTimePickerDataN.Text + "','" + textBoxTelefon.Text + "','" + textBoxEmail2.Text + "',(select id from localitati where localitate='" + comboBoxLocalitate.Text + "'),(select id from regiuni where regiune='" + comboBoxRegiune.Text + "' and id_tara=(select id from tari where tara='" + comboBoxTara.Text + "')),(select id from tari where tara='" + comboBoxTara.Text + "'),(select id from continente where continent='" + comboBoxContinent.Text + "'),'" + textBoxParolaNoua.Text + "')";
                cmd.ExecuteNonQuery();
                MessageBox.Show("The account has been created!", "Success!!!", MessageBoxButtons.OK, MessageBoxIcon.None);
                con.Close();
                stergereCasute();
                panelCreareCont.Hide();
                panelLogare.Show();
            }
            else
                if(textBoxNume.Text == "" && textBoxPrenume.Text == "" && textBoxTelefon.Text == "" && textBoxEmail2.Text == "" && textBoxParolaNoua.Text == "" && textBoxParolaVerificare.Text == "" && comboBoxContinent.Text == "" && comboBoxTara.Text == "" && comboBoxRegiune.Text == "" && comboBoxLocalitate.Text == "")
                    MessageBox.Show("Complete all the fields in order to create an account!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                else
                    if (textBoxPrenume.Text == "")
                        MessageBox.Show("Add your first name! You need an identity between us :D", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                    else
                        if(textBoxNume.Text == "")
                            MessageBox.Show("Add your last name! You need an identity between us :D", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                        else
                            if(textBoxTelefon.Text == "")
                                MessageBox.Show("Add a phone number to be able to contact you about any problem about your reservation!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                            else
                                if(textBoxEmail2.Text == "")
                                    MessageBox.Show("Add an email address, so you can access our aplication!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                                else
                                    if(textBoxParolaNoua.Text == "")
                                        MessageBox.Show("Please create your password! Be careful to be strong, so your account won't be stolen by anybody.", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                                    else
                                        if(textBoxParolaVerificare.Text == "")
                                            MessageBox.Show("Please confirm your password! This way you will confirm yourself it's correct.", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                                        else
                                            if(comboBoxContinent.Text == "")
                                                MessageBox.Show("Please select the continent you are from! It helps us with some information that will help you when you make a reservation.", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                                            else
                                                if(comboBoxTara.Text == "")
                                                    MessageBox.Show("Please select the country you are from! It helps us with some information that will help you when you make a reservation.", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                                                else
                                                    if(comboBoxRegiune.Text == "")
                                                        MessageBox.Show("Please select the region your locality is located! It helps us with some information that will help you when you make a reservation.", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                                                    else
                                                        if(comboBoxLocalitate.Text == "")
                                                            MessageBox.Show("Please select the locality you are from! It helps us with some information that will help you when you make a reservation.", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                                                        else
                                                            if(textBoxParolaNoua.Text != textBoxParolaVerificare.Text)
                                                                MessageBox.Show("The passwords do not match!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                                                            else
                                                                if(an < 18)
                                                                    MessageBox.Show("You must be at least 18 years old to create an account!", "Error!!!", MessageBoxButtons.OK, MessageBoxIcon.Error);
                                                                else
                                                                    if(!c)
                                                                        MessageBox.Show("Invalid email format!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                                                                    else
                                                                        if(d == 0)
                                                                            MessageBox.Show("The email already exists!", "Error!!!", MessageBoxButtons.OK, MessageBoxIcon.Error);
            textBoxParolaNoua.Text = "";
            textBoxParolaVerificare.Text = "";
        }

        private void textBoxParolaVerificare_TextChanged(object sender, EventArgs e)
        {
            if (textBoxParolaNoua.Text != textBoxParolaVerificare.Text)
                labelParola.Show();
            else
                labelParola.Hide();
            if (textBoxParolaVerificare.Text == "")
                labelParola.Hide();
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

        private void textBoxPrenume_KeyPress(object sender, KeyPressEventArgs e)
        {
            if(!char.IsLetter(e.KeyChar) && !char.IsControl(e.KeyChar) && !char.IsWhiteSpace(e.KeyChar) && e.KeyChar != '-')
            {
                e.Handled = true;
                MessageBox.Show("Only letters are allowed in this field!", "Error!!!", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            else
                e.Handled = false;
        }

        private void textBoxNume_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (textBoxNume.Text.StartsWith(" ") && e.KeyChar == ' ')
            {
                e.Handled = true;
                MessageBox.Show("Space can't be the first character!", "Error!!!", MessageBoxButtons.OK, MessageBoxIcon.Error);
                textBoxNume.Clear();
            }
            else
               if (!char.IsLetter(e.KeyChar) && !char.IsControl(e.KeyChar) && !char.IsWhiteSpace(e.KeyChar) && e.KeyChar != '-')
               {
                    e.Handled = true;
                    MessageBox.Show("Only letters are allowed in this field!", "Error!!!", MessageBoxButtons.OK, MessageBoxIcon.Error);
               }
               else
                   e.Handled = false;
        }

        private void textBoxPrenume_TextChanged(object sender, EventArgs e)
        {
            if (textBoxPrenume.Text.StartsWith(" "))
            {
                MessageBox.Show("Space can't be the first character!", "Error!!!", MessageBoxButtons.OK, MessageBoxIcon.Error);
                textBoxPrenume.Clear();
            }
        }

        private void textBoxNume_TextChanged(object sender, EventArgs e)
        {
            if (textBoxNume.Text.StartsWith(" "))
            {
                MessageBox.Show("Space can't be the first character!", "Error!!!", MessageBoxButtons.OK, MessageBoxIcon.Error);
                textBoxNume.Clear();
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

        private void textBoxParolaNoua_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (char.IsWhiteSpace(e.KeyChar))
            {
                e.Handled = true;
                MessageBox.Show("Space is not allowed in this field!", "Error!!!", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            else
                e.Handled = false;
        }

        private void textBoxEmail2_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (char.IsWhiteSpace(e.KeyChar))
            {
                e.Handled = true;
                MessageBox.Show("Space is not allowed in this field!", "Error!!!", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            else
                e.Handled = false;
        }

        private void textBoxParolaVerificare_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (char.IsWhiteSpace(e.KeyChar))
            {
                e.Handled = true;
                MessageBox.Show("Space is not allowed in this field!", "Error!!!", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            else
                e.Handled = false;
        }

        private void textBoxEmail_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar == Convert.ToChar(Keys.Enter))
            {
                if (textBoxEmail.Text != "" && textBoxParola.Text != "")
                {
                    con.Open();
                    cmd.CommandText = "select parola from clienti where email='" + textBoxEmail.Text + "'";
                    dr = cmd.ExecuteReader();
                    if (dr.HasRows)
                        while (dr.Read())
                            textBoxVerificareParola.Text = dr[0].ToString();
                    con.Close();
                    if (textBoxParola.Text == textBoxVerificareParola.Text)
                    {
                        if (k == 1)
                        {
                            Agentie_de_turism.Properties.Settings.Default.email = textBoxEmail.Text;
                            Agentie_de_turism.Properties.Settings.Default.parola = textBoxParola.Text;
                        }
                        else
                        {
                            Agentie_de_turism.Properties.Settings.Default.email = "";
                            Agentie_de_turism.Properties.Settings.Default.parola = "";
                        }
                        Vizitator viz = new Vizitator(textBoxEmail.Text);
                        this.Close();
                        viz.ShowDialog();
                    }
                    else
                    {
                        MessageBox.Show("One of the following fields are wrong: email, password!", "Error!!!", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        con.Close();
                    }
                }
                else
                    if (textBoxEmail.Text == "" && textBoxParola.Text == "")
                        MessageBox.Show("Complete all the fields in order to login!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                    else
                        if (textBoxEmail.Text == "")
                            MessageBox.Show("Add an email in the email field!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                        else
                            if (textBoxParola.Text == "")
                                MessageBox.Show("Add a password in the field password!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
            }
        }

        private void textBoxParola_KeyPress(object sender, KeyPressEventArgs e)
        {
            if(e.KeyChar == Convert.ToChar(Keys.Enter))
            {
                if (textBoxEmail.Text != "" && textBoxParola.Text != "")
                {
                    con.Open();
                    cmd.CommandText = "select parola from clienti where email='" + textBoxEmail.Text + "'";
                    dr = cmd.ExecuteReader();
                    if (dr.HasRows)
                        while (dr.Read())
                            textBoxVerificareParola.Text = dr[0].ToString();
                    con.Close();
                    if (textBoxParola.Text == textBoxVerificareParola.Text)
                    {
                        if (k == 1)
                        {
                            Agentie_de_turism.Properties.Settings.Default.email = textBoxEmail.Text;
                            Agentie_de_turism.Properties.Settings.Default.parola = textBoxParola.Text;
                        }
                        else
                        {
                            Agentie_de_turism.Properties.Settings.Default.email = "";
                            Agentie_de_turism.Properties.Settings.Default.parola = "";
                        }
                        Vizitator viz = new Vizitator(textBoxEmail.Text);
                        this.Close();
                        viz.ShowDialog();
                    }
                    else
                    {
                        MessageBox.Show("One of the following fields are wrong: email, password!", "Error!!!", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        con.Close();
                    }
                }
                else
                    if (textBoxEmail.Text == "" && textBoxParola.Text == "")
                        MessageBox.Show("Complete all the fields in order to login!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                    else
                        if (textBoxEmail.Text == "")
                            MessageBox.Show("Add an email in the email field!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                        else
                            if (textBoxParola.Text == "")
                                MessageBox.Show("Add a password in the field password!", "Warning!!!", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
            }
        }

        private void textBoxParolaNoua_TextChanged(object sender, EventArgs e)
        {
            if (textBoxParolaNoua.Text == "" && textBoxParolaVerificare.Text == "")
                labelParola.Hide();
        }

        private void checkBoxRetinere_CheckedChanged(object sender, EventArgs e)
        {
            if(textBoxEmail.Text != "" && textBoxParola.Text != "" && schimba == 1)
            {
                k++;
                if (k > 1)
                    k = 0;
                Agentie_de_turism.Properties.Settings.Default.retineCont = k;
            }
        }
    }
}
