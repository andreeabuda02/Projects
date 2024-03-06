using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Agentie_de_turism
{
    public partial class Form1 : Form
    {
        private float ADx, ADy;
        private float BIx,BIy;
        public Form1()
        {
            InitializeComponent();
            ADx = buttonAdmin.Location.X / (float)panelMeniu.Width;
            ADy = buttonAdmin.Location.Y / (float)panelMeniu.Height;
            BIx = buttonIesire.Location.X / (float)panelMeniu.Width;
            BIy = buttonIesire.Location.Y / (float)panelMeniu.Height;
        }

        private void buttonIesire_Click(object sender, EventArgs e)
        {
            var rez = MessageBox.Show("Are you sure?", "Reminder", MessageBoxButtons.YesNo, MessageBoxIcon.Question);
            if(rez == DialogResult.Yes)
                this.Close();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Vizitator = 0;
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 0;
            FormBorderStyle = FormBorderStyle.None;
            WindowState = FormWindowState.Maximized;
            pictureBoxFundal.Dock = DockStyle.Fill;
            panelMeniu.Dock = DockStyle.Fill;
            panelAdmin.Dock = DockStyle.Fill;
            buttonAdmin.Location = new Point((int)(panelMeniu.Width * ADx), (int)(panelMeniu.Height * ADy));
            buttonVizitator.Location = new Point((int)(buttonAdmin.Location.X + 400), (int)(buttonAdmin.Location.Y));
            textBoxParola.Location = new Point(panelAdmin.Width / 2 - textBoxParola.Width / 2 + panelAdmin.Location.X, panelAdmin.Height / 2 - textBoxParola.Height / 2 + panelAdmin.Location.Y);
            labelParola.Location = new Point(textBoxParola.Location.X - 160, textBoxParola.Location.Y - 7);
            buttonLogare.Location = new Point(textBoxParola.Location.X + 100, textBoxParola.Location.Y +40);
            buttonIesire.Location = new Point((int)(panelMeniu.Width * BIx), (int)(panelMeniu.Height * BIy));
            panelAdmin.Hide();
            panelMeniu.Show();
            panelAdmin.BackgroundImage = Agentie_de_turism.Properties.Resources.Admin;
        }

        private void buttonAdmin_Click(object sender, EventArgs e)
        {
            panelMeniu.Hide();
            panelAdmin.Show();
        }

        private void buttonVizitator_Click(object sender, EventArgs e)
        {
            panelAdmin.Hide();
            ConectareANDInregistrare CI = new ConectareANDInregistrare();
            CI.ShowDialog();
            this.Close();
        }

        private void textBoxParola_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (textBoxParola.Text == "admin" && e.KeyChar == Convert.ToChar(Keys.Enter))
            {
                panelAdmin.Hide();
                panelMeniu.Show();
                textBoxParola.Clear();
                Adaugare ad = new Adaugare();
                ad.ShowDialog();
            }
            else
                if(e.KeyChar == Convert.ToChar(Keys.Enter))
                {
                    MessageBox.Show("Wrong password", "Error!!!", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    textBoxParola.Clear();
                }
        }

        private void buttonInapoi_Click(object sender, EventArgs e)
        {
            panelAdmin.Hide();
            panelMeniu.Show();
        }

        private void panelAdmin_Paint(object sender, PaintEventArgs e)
        {

        }

        private void buttonLogare_Click(object sender, EventArgs e)
        {
            if (textBoxParola.Text == "admin")
            {
                panelAdmin.Hide();
                panelMeniu.Show();
                textBoxParola.Clear();
                Adaugare ad = new Adaugare();
                ad.ShowDialog();
            }
            else
            {
                MessageBox.Show("Wrong password", "Error!!!", MessageBoxButtons.OK, MessageBoxIcon.Error);
                textBoxParola.Clear();
            }
        }
    }
}
