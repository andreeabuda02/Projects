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
    public partial class America_de_Sud : Form
    {
        public America_de_Sud(string Cont, string Continent)
        {
            InitializeComponent();
            textBoxContinent.Text = Continent.ToString();
            textBoxCont.Text = Cont.ToString();
        }
        private void America_de_Sud_Load(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Vizitator = 0;
            Agentie_de_turism.Properties.Settings.Default.Continent = 3;
            Agentie_de_turism.Properties.Settings.Default.Tara = 0;
            FormBorderStyle = FormBorderStyle.None;
            WindowState = FormWindowState.Maximized;
            textBoxContinent.Hide();
            textBoxTara.Hide();
            textBoxCont.Hide();
        }

        private void buttonInapoi_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Vizitator = 1;
            Vizitator viz = new Vizitator(textBoxCont.Text);
            this.Close();
            viz.ShowDialog();
        }

        private void buttonDetaliiCont_Click(object sender, EventArgs e)
        {
            DetaliiCont dc = new DetaliiCont(textBoxCont.Text);
            dc.ShowDialog();
            this.Close();
        }
    }
}
