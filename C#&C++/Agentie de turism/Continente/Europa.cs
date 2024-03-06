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
    public partial class Europa : Form
    {
        private float Islandax, Islanday;
        private float Norvegiax, Norvegiay;
        private float Suediax, Suediay;
        private float Finlandax, Finlanday;
        private float Rusiax, Rusiay;
        private float Estoniax, Estoniay;
        private float Letoniax, Letoniay;
        private float Lituaniax, Lituaniay;
        private float Belarusx, Belarusy;
        private float Ucrainax, Ucrainay;
        private float Moldovax, Moldovay;
        private float Romaniax, Romaniay;
        private float Bulgariax, Bulgariay;
        private float Greciax, Greciay;
        private float Macedoniax, Macedoniay;
        private float Albaniax, Albaniay;
        private float Muntenegrux, Muntenegruy;
        private float Kosovox, Kosovoy;
        private float Serbiax, Serbiay;
        private float BosniaHx, BosniaHy;
        private float Croatiax, Croatiay;
        private float Sloveniax, Sloveniay;
        private float Ungariax, Ungariay;
        private float Slovaciax, Slovaciay;
        private float Poloniax, Poloniay;
        private float RepCehax, RepCehay;
        private float Austriax, Austriay;
        private float Elvetiax, Elvetiay;
        private float Danemarcax, Danemarcay;
        private float Germaniax, Germaniay;
        private float Luxemburgx, Luxemburgy;
        private float Liechtensteinx, Liechtensteiny;
        private float Olandax, Olanday;
        private float Belgiax, Belgiay;
        private float Frantax, Frantay;
        private float Andorrax, Andorray;
        private float Spaniax, Spaniay;
        private float Portugaliax, Portugaliay;
        private float Monacox, Monacoy;
        private float Italiax, Italiay;
        private float SanMarinox, SanMarinoy;
        private float Vaticanx, Vaticany;
        private float Maltax, Maltay;
        private float Ciprux, Cipruy;
        private float Irlandax, Irlanday;
        private float RegatulUx, RegatulUy;

        private void radioButtonIslanda_MouseEnter(object sender, EventArgs e)
        {
            radioButtonIslanda.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelIslanda.Show();
        }

        private void radioButtonIslanda_MouseLeave(object sender, EventArgs e)
        {
            radioButtonIslanda.BackgroundImage = null;
            panelIslanda.Hide();
        }

        private void radioButtonNorvegia_MouseEnter(object sender, EventArgs e)
        {
            radioButtonNorvegia.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelNorvegia.Show();
        }

        private void radioButtonNorvegia_MouseLeave(object sender, EventArgs e)
        {
            radioButtonNorvegia.BackgroundImage = null;
            panelNorvegia.Hide();
        }

        private void radioButtonSuedia_MouseEnter(object sender, EventArgs e)
        {
            radioButtonSuedia.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelSuedia.Show();
        }

        private void radioButtonSuedia_MouseLeave(object sender, EventArgs e)
        {
            radioButtonSuedia.BackgroundImage = null;
            panelSuedia.Hide();
        }

        private void radioButtonFinlanda_MouseEnter(object sender, EventArgs e)
        {
            radioButtonFinlanda.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelFinlanda.Show();
        }

        private void radioButtonFinlanda_MouseLeave(object sender, EventArgs e)
        {
            radioButtonFinlanda.BackgroundImage = null;
            panelFinlanda.Hide();
        }

        private void radioButtonRusia_MouseEnter(object sender, EventArgs e)
        {
            radioButtonRusia.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelRusia.Show();
        }

        private void radioButtonRusia_MouseLeave(object sender, EventArgs e)
        {
            radioButtonRusia.BackgroundImage = null;
            panelRusia.Hide();
        }

        private void radioButtonEstonia_MouseEnter(object sender, EventArgs e)
        {
            radioButtonEstonia.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelEstonia.Show();
        }

        private void radioButtonEstonia_MouseLeave(object sender, EventArgs e)
        {
            radioButtonEstonia.BackgroundImage = null;
            panelEstonia.Hide();
        }

        private void radioButtonLetonia_MouseEnter(object sender, EventArgs e)
        {
            radioButtonLetonia.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelLetonia.Show();
        }

        private void radioButtonLetonia_MouseLeave(object sender, EventArgs e)
        {
            radioButtonLetonia.BackgroundImage = null;
            panelLetonia.Hide();
        }

        private void radioButtonLituania_MouseEnter(object sender, EventArgs e)
        {
            radioButtonLituania.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelLituania.Show();
        }

        private void radioButtonLituania_MouseLeave(object sender, EventArgs e)
        {
            radioButtonLituania.BackgroundImage = null;
            panelLituania.Hide();
        }

        private void radioButtonBelarus_MouseEnter(object sender, EventArgs e)
        {
            radioButtonBelarus.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelBelarus.Show();
        }

        private void radioButtonBelarus_MouseLeave(object sender, EventArgs e)
        {
            radioButtonBelarus.BackgroundImage = null;
            panelBelarus.Hide();
        }

        private void radioButtonUcraina_MouseEnter(object sender, EventArgs e)
        {
            radioButtonUcraina.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelUcraina.Show();
        }

        private void radioButtonUcraina_MouseLeave(object sender, EventArgs e)
        {
            radioButtonUcraina.BackgroundImage = null;
            panelUcraina.Hide();
        }

        private void radioButtonMoldova_MouseEnter(object sender, EventArgs e)
        {
            radioButtonMoldova.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelMoldova.Show();
        }

        private void radioButtonMoldova_MouseLeave(object sender, EventArgs e)
        {
            radioButtonMoldova.BackgroundImage = null;
            panelMoldova.Hide();
        }

        private void radioButtonRomania_MouseEnter(object sender, EventArgs e)
        {
            radioButtonRomania.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelRomania.Show();
        }

        private void radioButtonRomania_MouseLeave(object sender, EventArgs e)
        {
            radioButtonRomania.BackgroundImage = null;
            panelRomania.Hide();
        }

        private void radioButtonBulgaria_MouseEnter(object sender, EventArgs e)
        {
            radioButtonBulgaria.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelBulgaria.Show();
        }

        private void radioButtonBulgaria_MouseLeave(object sender, EventArgs e)
        {
            radioButtonBulgaria.BackgroundImage = null;
            panelBulgaria.Hide();
        }

        private void radioButtonGrecia_MouseEnter(object sender, EventArgs e)
        {
            radioButtonGrecia.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelGrecia.Show();
        }

        private void radioButtonGrecia_MouseLeave(object sender, EventArgs e)
        {
            radioButtonGrecia.BackgroundImage = null;
            panelGrecia.Hide();
        }

        private void radioButtonMacedonia_MouseEnter(object sender, EventArgs e)
        {
            radioButtonMacedonia.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelMacedonia.Show();
        }

        private void radioButtonMacedonia_MouseLeave(object sender, EventArgs e)
        {
            radioButtonMacedonia.BackgroundImage = null;
            panelMacedonia.Hide();
        }

        private void radioButtonAlbania_MouseEnter(object sender, EventArgs e)
        {
            radioButtonAlbania.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelAlbania.Show();
        }

        private void radioButtonAlbania_MouseLeave(object sender, EventArgs e)
        {
            radioButtonAlbania.BackgroundImage = null;
            panelAlbania.Hide();
        }

        private void radioButtonKosovo_MouseEnter(object sender, EventArgs e)
        {
            radioButtonKosovo.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelKosovo.Show();
        }

        private void radioButtonKosovo_MouseLeave(object sender, EventArgs e)
        {
            radioButtonKosovo.BackgroundImage = null;
            panelKosovo.Hide();
        }

        private void radioButtonMuntenegru_MouseEnter(object sender, EventArgs e)
        {
            radioButtonMuntenegru.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelMuntenegru.Show();
        }

        private void radioButtonMuntenegru_MouseLeave(object sender, EventArgs e)
        {
            radioButtonMuntenegru.BackgroundImage = null;
            panelMuntenegru.Hide();
        }

        private void radioButtonSerbia_MouseEnter(object sender, EventArgs e)
        {
            radioButtonSerbia.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelSerbia.Show();
        }

        private void radioButtonSerbia_MouseLeave(object sender, EventArgs e)
        {
            radioButtonSerbia.BackgroundImage = null;
            panelSerbia.Hide();
        }

        private void radioButtonBosniaH_MouseEnter(object sender, EventArgs e)
        {
            radioButtonBosniaH.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelBosniaH.Show();
        }

        private void radioButtonBosniaH_MouseLeave(object sender, EventArgs e)
        {
            radioButtonBosniaH.BackgroundImage = null;
            panelBosniaH.Hide();
        }

        private void radioButtonCroatia_MouseEnter(object sender, EventArgs e)
        {
            radioButtonCroatia.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelCroatia.Show();
        }

        private void radioButtonCroatia_MouseLeave(object sender, EventArgs e)
        {
            radioButtonCroatia.BackgroundImage = null;
            panelCroatia.Hide();
        }

        private void radioButtonUngaria_MouseEnter(object sender, EventArgs e)
        {
            radioButtonUngaria.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelUngaria.Show();
        }

        private void radioButtonUngaria_MouseLeave(object sender, EventArgs e)
        {
            radioButtonUngaria.BackgroundImage = null;
            panelUngaria.Hide();
        }

        private void radioButtonSlovacia_MouseEnter(object sender, EventArgs e)
        {
            radioButtonSlovacia.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelSlovacia.Show();
        }

        private void radioButtonSlovacia_MouseLeave(object sender, EventArgs e)
        {
            radioButtonSlovacia.BackgroundImage = null;
            panelSlovacia.Hide();
        }

        private void radioButtonSlovenia_MouseEnter(object sender, EventArgs e)
        {
            radioButtonSlovenia.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelSlovenia.Show();
        }

        private void radioButtonSlovenia_MouseLeave(object sender, EventArgs e)
        {
            radioButtonSlovenia.BackgroundImage = null;
            panelSlovenia.Hide();
        }

        private void radioButtonAustria_MouseEnter(object sender, EventArgs e)
        {
            radioButtonAustria.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelAustria.Show();
        }

        private void radioButtonAustria_MouseLeave(object sender, EventArgs e)
        {
            radioButtonAustria.BackgroundImage = null;
            panelAustria.Hide();
        }

        private void radioButtonRepCeha_MouseEnter(object sender, EventArgs e)
        {
            radioButtonRepCeha.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelRepCeha.Show();
        }

        private void radioButtonRepCeha_MouseLeave(object sender, EventArgs e)
        {
            radioButtonRepCeha.BackgroundImage = null;
            panelRepCeha.Hide();
        }

        private void radioButtonPolonia_MouseEnter(object sender, EventArgs e)
        {
            radioButtonPolonia.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelPolonia.Show();
        }

        private void radioButtonPolonia_MouseLeave(object sender, EventArgs e)
        {
            radioButtonPolonia.BackgroundImage = null;
            panelPolonia.Hide();
        }

        private void radioButtonDanemarca_MouseEnter(object sender, EventArgs e)
        {
            radioButtonDanemarca.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelDanemarca.Show();
        }

        private void radioButtonDanemarca_MouseLeave(object sender, EventArgs e)
        {
            radioButtonDanemarca.BackgroundImage = null;
            panelDanemarca.Hide();
        }

        private void radioButtonGermania_MouseEnter(object sender, EventArgs e)
        {
            radioButtonGermania.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelGermania.Show();
        }

        private void radioButtonGermania_MouseLeave(object sender, EventArgs e)
        {
            radioButtonGermania.BackgroundImage = null;
            panelGermania.Hide();
        }

        private void radioButtonLiechtenstein_MouseEnter(object sender, EventArgs e)
        {
            radioButtonLiechtenstein.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelLiechtenstein.Show();
        }

        private void radioButtonLiechtenstein_MouseLeave(object sender, EventArgs e)
        {
            radioButtonLiechtenstein.BackgroundImage = null;
            panelLiechtenstein.Hide();
        }

        private void radioButtonElvetia_MouseEnter(object sender, EventArgs e)
        {
            radioButtonElvetia.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelElvetia.Show();
        }

        private void radioButtonElvetia_MouseLeave(object sender, EventArgs e)
        {
            radioButtonElvetia.BackgroundImage = null;
            panelElvetia.Hide();
        }

        private void radioButtonItalia_MouseEnter(object sender, EventArgs e)
        {
            radioButtonItalia.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelItalia.Show();
        }

        private void radioButtonItalia_MouseLeave(object sender, EventArgs e)
        {
            radioButtonItalia.BackgroundImage = null;
            panelItalia.Hide();
        }

        private void radioButtonSanMarino_MouseEnter(object sender, EventArgs e)
        {
            radioButtonSanMarino.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelSanMarino.Show();
        }

        private void radioButtonSanMarino_MouseLeave(object sender, EventArgs e)
        {
            radioButtonSanMarino.BackgroundImage = null;
            panelSanMarino.Hide();
        }

        private void radioButtonVatican_MouseEnter(object sender, EventArgs e)
        {
            radioButtonVatican.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelVatican.Show();
        }

        private void radioButtonVatican_MouseLeave(object sender, EventArgs e)
        {
            radioButtonVatican.BackgroundImage = null;
            panelVatican.Hide();
        }

        private void radioButtonCipru_MouseEnter(object sender, EventArgs e)
        {
            radioButtonCipru.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelCipru.Show();
        }

        private void radioButtonCipru_MouseLeave(object sender, EventArgs e)
        {
            radioButtonCipru.BackgroundImage = null;
            panelCipru.Hide();
        }

        private void radioButtonMalta_MouseEnter(object sender, EventArgs e)
        {
            radioButtonMalta.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelMalta.Show();
        }

        private void radioButtonMalta_MouseLeave(object sender, EventArgs e)
        {
            radioButtonMalta.BackgroundImage = null;
            panelMalta.Hide();
        }

        private void radioButtonMonaco_MouseEnter(object sender, EventArgs e)
        {
            radioButtonMonaco.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelMonaco.Show();
        }

        private void radioButtonMonaco_MouseLeave(object sender, EventArgs e)
        {
            radioButtonMonaco.BackgroundImage = null;
            panelMonaco.Hide();
        }

        private void radioButtonLuxemburg_MouseEnter(object sender, EventArgs e)
        {
            radioButtonLuxemburg.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelLuxemburg.Show();
        }

        private void radioButtonLuxemburg_MouseLeave(object sender, EventArgs e)
        {
            radioButtonLuxemburg.BackgroundImage = null;
            panelLuxemburg.Hide();
        }

        private void radioButtonBelgia_MouseEnter(object sender, EventArgs e)
        {
            radioButtonBelgia.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelBelgia.Show();
        }

        private void radioButtonBelgia_MouseLeave(object sender, EventArgs e)
        {
            radioButtonBelgia.BackgroundImage = null;
            panelBelgia.Hide();
        }

        private void radioButtonOlanda_MouseEnter(object sender, EventArgs e)
        {
            radioButtonOlanda.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelOlanda.Show();
        }

        private void radioButtonOlanda_MouseLeave(object sender, EventArgs e)
        {
            radioButtonOlanda.BackgroundImage = null;
            panelOlanda.Hide();
        }

        private void radioButtonFranta_MouseEnter(object sender, EventArgs e)
        {
            radioButtonFranta.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelFranta.Show();
        }

        private void radioButtonFranta_MouseLeave(object sender, EventArgs e)
        {
            radioButtonFranta.BackgroundImage = null;
            panelFranta.Hide();
        }

        private void radioButtonAndorra_MouseEnter(object sender, EventArgs e)
        {
            radioButtonAndorra.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelAndorra.Show();
        }

        private void radioButtonAndorra_MouseLeave(object sender, EventArgs e)
        {
            radioButtonAndorra.BackgroundImage = null;
            panelAndorra.Hide();
        }

        private void radioButtonSpania_MouseEnter(object sender, EventArgs e)
        {
            radioButtonSpania.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelSpania.Show();
        }

        private void radioButtonSpania_MouseLeave(object sender, EventArgs e)
        {
            radioButtonSpania.BackgroundImage = null;
            panelSpania.Hide();
        }

        private void radioButtonPortugalia_MouseEnter(object sender, EventArgs e)
        {
            radioButtonPortugalia.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelPortugalia.Show();
        }

        private void radioButtonPortugalia_MouseLeave(object sender, EventArgs e)
        {
            radioButtonPortugalia.BackgroundImage = null;
            panelPortugalia.Hide();
        }

        private void radioButtonRegatulU_MouseEnter(object sender, EventArgs e)
        {
            radioButtonRegatulU.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelRegatulU.Show();
        }

        private void radioButtonRegatulU_MouseLeave(object sender, EventArgs e)
        {
            radioButtonRegatulU.BackgroundImage = null;
            panelRegatulU.Hide();
        }

        private void radioButtonIrlanda_MouseEnter(object sender, EventArgs e)
        {
            radioButtonIrlanda.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelIrlanda.Show();
        }

        private void radioButtonIrlanda_MouseLeave(object sender, EventArgs e)
        {
            radioButtonIrlanda.BackgroundImage = null;
            panelIrlanda.Hide();
        }

        private void radioButtonIslanda_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 2;
            textBoxTara.Text = "Iceland";
            Islanda irlanda = new Islanda(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            irlanda.ShowDialog();
            this.Close();
        }

        private void radioButtonNorvegia_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 3;
            textBoxTara.Text = "Norway";
            Norvegia norvegia = new Norvegia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            norvegia.ShowDialog();
            this.Close();
        }

        private void radioButtonSuedia_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 4;
            textBoxTara.Text = "Sweden";
            Suedia suedia = new Suedia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            suedia.ShowDialog();
            this.Close();
        }

        private void radioButtonFinlanda_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 5;
            textBoxTara.Text = "Finland";
            Finlanda finlanda = new Finlanda(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            finlanda.ShowDialog();
            this.Close();
        }

        private void radioButtonEstonia_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 6;
            textBoxTara.Text = "Estonia";
            Estonia estonia = new Estonia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            estonia.ShowDialog();
            this.Close();
        }

        private void radioButtonRusia_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 7;
            textBoxTara.Text = "Russia";
            Rusia rusia = new Rusia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            rusia.ShowDialog();
            this.Close();
        }

        private void radioButtonLetonia_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 8;
            textBoxTara.Text = "Latvia";
            Letonia letonia = new Letonia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            letonia.ShowDialog();
            this.Close();
        }

        private void radioButtonLituania_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 9;
            textBoxTara.Text = "Lithuania";
            Lituania lituania = new Lituania(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            lituania.ShowDialog();
            this.Close();
        }

        private void radioButtonBelarus_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 10;
            textBoxTara.Text = "Belarus";
            Belarus belarus = new Belarus(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            belarus.ShowDialog();
            this.Close();
        }

        private void radioButtonUcraina_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 11;
            textBoxTara.Text = "Ukraine";
            Ucraina ucraina = new Ucraina(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            ucraina.ShowDialog();
            this.Close();
        }

        private void buttonDetaliiCont_Click(object sender, EventArgs e)
        {
            DetaliiCont dc = new DetaliiCont(textBoxCont.Text);
            dc.ShowDialog();
            this.Close();
        }

        private void radioButtonMoldova_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 12;
            textBoxTara.Text = "Moldavia";
            RepMoldova moldova = new RepMoldova(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            moldova.ShowDialog();
            this.Close();
        }

        private void radioButtonRomania_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 1;
            textBoxTara.Text = "Romania";
            Romania romania = new Romania(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            romania.ShowDialog();
            this.Close();
        }

        private void radioButtonBulgaria_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 13;
            textBoxTara.Text = "Bulgaria";
            Bulgaria bulgaria = new Bulgaria(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            bulgaria.ShowDialog();
            this.Close();
        }

        private void radioButtonCipru_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 14;
            textBoxTara.Text = "Cyprus";
            Cipru cipru = new Cipru(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            cipru.ShowDialog();
            this.Close();
        }

        private void radioButtonMalta_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 15;
            textBoxTara.Text = "Malta";
            Malta malta = new Malta(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            malta.ShowDialog();
            this.Close();
        }

        private void radioButtonGrecia_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 16;
            textBoxTara.Text = "Greece";
            Grecia grecia = new Grecia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            grecia.ShowDialog();
            this.Close();
        }

        private void radioButtonMacedonia_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 17;
            textBoxTara.Text = "North Macedonia";
            Macedonia macedonia = new Macedonia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            macedonia.ShowDialog();
            this.Close();
        }

        private void radioButtonAlbania_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 18;
            textBoxTara.Text = "Albania";
            Albania albania = new Albania(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            albania.ShowDialog();
            this.Close();
        }

        private void radioButtonKosovo_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 19;
            textBoxTara.Text = "Kosovo";
            Kosovo kosovo = new Kosovo(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            kosovo.ShowDialog();
            this.Close();
        }

        private void radioButtonMuntenegru_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 20;
            textBoxTara.Text = "Montenegro";
            Muntenegru muntenegru = new Muntenegru(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            muntenegru.ShowDialog();
            this.Close();
        }

        private void radioButtonSerbia_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 21;
            textBoxTara.Text = "Serbia";
            Serbia serbia = new Serbia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            serbia.ShowDialog();
            this.Close();
        }

        private void radioButtonBosniaH_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 22;
            textBoxTara.Text = "Bosnia and Herzegovina";
            BosniaHertegovina bosniaHertegovina = new BosniaHertegovina(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            bosniaHertegovina.ShowDialog();
            this.Close();
        }

        private void radioButtonCroatia_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 23;
            textBoxTara.Text = "Croatia";
            Croatia croatia = new Croatia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            croatia.ShowDialog();
            this.Close();
        }

        private void radioButtonUngaria_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 24;
            textBoxTara.Text = "Hungary";
            Ungaria ungaria = new Ungaria(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            ungaria.ShowDialog();
            this.Close();
        }

        private void radioButtonSlovenia_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 25;
            textBoxTara.Text = "Slovenia";
            Slovenia slovenia = new Slovenia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            slovenia.ShowDialog();
            this.Close();
        }

        private void radioButtonSlovacia_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 26;
            textBoxTara.Text = "Slovakia";
            Slovacia slovacia = new Slovacia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            slovacia.ShowDialog();
            this.Close();
        }

        private void radioButtonAustria_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 27;
            textBoxTara.Text = "Austria";
            Austria austria = new Austria(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            austria.ShowDialog();
            this.Close();
        }

        private void radioButtonRepCeha_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 28;
            textBoxTara.Text = "Czech Republic";
            RepCeha repCeha = new RepCeha(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            repCeha.ShowDialog();
            this.Close();
        }

        private void radioButtonPolonia_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 29;
            textBoxTara.Text = "Poland";
            Polonia polonia = new Polonia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            polonia.ShowDialog();
            this.Close();
        }

        private void radioButtonDanemarca_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 30;
            textBoxTara.Text = "Denmark";
            Danemarca danemarca = new Danemarca(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            danemarca.ShowDialog();
            this.Close();
        }

        private void radioButtonGermania_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 31;
            textBoxTara.Text = "Germany";
            Germania germania = new Germania(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            germania.ShowDialog();
            this.Close();
        }

        private void radioButtonOlanda_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 32;
            textBoxTara.Text = "Netherlands";
            Olanda olanda = new Olanda(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            olanda.ShowDialog();
            this.Close();
        }

        private void radioButtonBelgia_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 33;
            textBoxTara.Text = "Belgium";
            Belgia belgia = new Belgia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            belgia.ShowDialog();
            this.Close();
        }

        private void radioButtonLuxemburg_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 34;
            textBoxTara.Text = "Luxembourg";
            Luxemburg luxemburg = new Luxemburg(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            luxemburg.ShowDialog();
            this.Close();
        }

        private void radioButtonLiechtenstein_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 35;
            textBoxTara.Text = "Liechtenstein";
            Liechtenstein liechtenstein = new Liechtenstein(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            liechtenstein.ShowDialog();
            this.Close();
        }

        private void radioButtonElvetia_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 36;
            textBoxTara.Text = "Switzerland";
            Elvetia elvetia = new Elvetia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            elvetia.ShowDialog();
            this.Close();
        }

        private void radioButtonItalia_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 37;
            textBoxTara.Text = "Italy";
            Italia italia = new Italia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            italia.ShowDialog();
            this.Close();
        }

        private void radioButtonSanMarino_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 38;
            textBoxTara.Text = "San Marino";
            SanMarino sanMarino = new SanMarino(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            sanMarino.ShowDialog();
            this.Close();
        }

        private void radioButtonVatican_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 39;
            textBoxTara.Text = "Vatican";
            Vatican vatican = new Vatican(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            vatican.ShowDialog();
            this.Close();
        }

        private void radioButtonMonaco_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 40;
            textBoxTara.Text = "Monaco";
            Monaco monaco = new Monaco(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            monaco.ShowDialog();
            this.Close();
        }

        private void radioButtonFranta_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 41;
            textBoxTara.Text = "France";
            Franta franta = new Franta(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            franta.ShowDialog();
            this.Close();
        }

        private void radioButtonAndorra_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 42;
            textBoxTara.Text = "Andorra";
            Andorra andorra = new Andorra(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            andorra.ShowDialog();
            this.Close();
        }

        private void radioButtonSpania_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 43;
            textBoxTara.Text = "Spain";
            Spania spania = new Spania(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            spania.ShowDialog();
            this.Close();
        }

        private void radioButtonPortugalia_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 44;
            textBoxTara.Text = "Portugal";
            Portugalia portugalia = new Portugalia(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            portugalia.ShowDialog();
            this.Close();
        }

        private void radioButtonIrlanda_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 45;
            textBoxTara.Text = "Ireland";
            Irlanda irlanda = new Irlanda(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            irlanda.ShowDialog();
            this.Close();

        }

        private void radioButtonRegatulU_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 46;
            textBoxTara.Text = "United Kingdom";
            RegatulUnit regatulUnit = new RegatulUnit(textBoxCont.Text, textBoxContinent.Text, textBoxTara.Text);
            regatulUnit.ShowDialog();
            this.Close();
        }

        void fixareCoordonate()
        {
            Romaniax = radioButtonRomania.Location.X / (float)pictureBoxFundal.Width;
            Romaniay = radioButtonRomania.Location.Y / (float)pictureBoxFundal.Height;
            Islandax = radioButtonIslanda.Location.X / (float)pictureBoxFundal.Width;
            Islanday = radioButtonIslanda.Location.Y / (float)pictureBoxFundal.Height;
            Norvegiax = radioButtonNorvegia.Location.X / (float)pictureBoxFundal.Width;
            Norvegiay = radioButtonNorvegia.Location.Y / (float)pictureBoxFundal.Height;
            Suediax = radioButtonSuedia.Location.X / (float)pictureBoxFundal.Width;
            Suediay = radioButtonSuedia.Location.Y / (float)pictureBoxFundal.Height;
            Finlandax = radioButtonFinlanda.Location.X / (float)pictureBoxFundal.Width;
            Finlanday = radioButtonFinlanda.Location.Y / (float)pictureBoxFundal.Height;
            Rusiax = radioButtonRusia.Location.X / (float)pictureBoxFundal.Width;
            Rusiay = radioButtonRusia.Location.Y / (float)pictureBoxFundal.Height;
            Estoniax = radioButtonEstonia.Location.X / (float)pictureBoxFundal.Width;
            Estoniay = radioButtonEstonia.Location.Y / (float)pictureBoxFundal.Height;
            Letoniax = radioButtonLetonia.Location.X / (float)pictureBoxFundal.Width;
            Letoniay = radioButtonLetonia.Location.Y / (float)pictureBoxFundal.Height;
            Lituaniax = radioButtonLituania.Location.X / (float)pictureBoxFundal.Width;
            Lituaniay = radioButtonLituania.Location.Y / (float)pictureBoxFundal.Height;
            Belarusx = radioButtonBelarus.Location.X / (float)pictureBoxFundal.Width;
            Belarusy = radioButtonBelarus.Location.Y / (float)pictureBoxFundal.Height;
            Ucrainax = radioButtonUcraina.Location.X / (float)pictureBoxFundal.Width;
            Ucrainay = radioButtonUcraina.Location.Y / (float)pictureBoxFundal.Height;
            Poloniax = radioButtonPolonia.Location.X / (float)pictureBoxFundal.Width;
            Poloniay = radioButtonPolonia.Location.Y / (float)pictureBoxFundal.Height;
            Germaniax = radioButtonGermania.Location.X / (float)pictureBoxFundal.Width;
            Germaniay = radioButtonGermania.Location.Y / (float)pictureBoxFundal.Height;
            Danemarcax = radioButtonDanemarca.Location.X / (float)pictureBoxFundal.Width;
            Danemarcay = radioButtonDanemarca.Location.Y / (float)pictureBoxFundal.Height;
            Irlandax = radioButtonIrlanda.Location.X / (float)pictureBoxFundal.Width;
            Irlanday = radioButtonIrlanda.Location.Y / (float)pictureBoxFundal.Height;
            RegatulUx = radioButtonRegatulU.Location.X / (float)pictureBoxFundal.Width;
            RegatulUy = radioButtonRegatulU.Location.Y / (float)pictureBoxFundal.Height;
            Belgiax = radioButtonBelgia.Location.X / (float)pictureBoxFundal.Width;
            Belgiay = radioButtonBelgia.Location.Y / (float)pictureBoxFundal.Height;
            Olandax = radioButtonOlanda.Location.X / (float)pictureBoxFundal.Width;
            Olanday = radioButtonOlanda.Location.Y / (float)pictureBoxFundal.Height;
            Luxemburgx = radioButtonLuxemburg.Location.X / (float)pictureBoxFundal.Width;
            Luxemburgy = radioButtonLuxemburg.Location.Y / (float)pictureBoxFundal.Height;
            Frantax = radioButtonFranta.Location.X / (float)pictureBoxFundal.Width;
            Frantay = radioButtonFranta.Location.Y / (float)pictureBoxFundal.Height;
            Andorrax = radioButtonAndorra.Location.X / (float)pictureBoxFundal.Width;
            Andorray = radioButtonAndorra.Location.Y / (float)pictureBoxFundal.Height;
            Spaniax = radioButtonSpania.Location.X / (float)pictureBoxFundal.Width;
            Spaniay = radioButtonSpania.Location.Y / (float)pictureBoxFundal.Height;
            Portugaliax = radioButtonPortugalia.Location.X / (float)pictureBoxFundal.Width;
            Portugaliay = radioButtonPortugalia.Location.Y / (float)pictureBoxFundal.Height;
            Italiax = radioButtonItalia.Location.X / (float)pictureBoxFundal.Width;
            Italiay = radioButtonItalia.Location.Y / (float)pictureBoxFundal.Height;
            Monacox = radioButtonMonaco.Location.X / (float)pictureBoxFundal.Width;
            Monacoy = radioButtonMonaco.Location.Y / (float)pictureBoxFundal.Height;
            Vaticanx = radioButtonVatican.Location.X / (float)pictureBoxFundal.Width;
            Vaticany = radioButtonVatican.Location.Y / (float)pictureBoxFundal.Height;
            SanMarinox = radioButtonSanMarino.Location.X / (float)pictureBoxFundal.Width;
            SanMarinoy = radioButtonSanMarino.Location.Y / (float)pictureBoxFundal.Height;
            Elvetiax = radioButtonElvetia.Location.X / (float)pictureBoxFundal.Width;
            Elvetiay = radioButtonElvetia.Location.Y / (float)pictureBoxFundal.Height;
            Liechtensteinx = radioButtonLiechtenstein.Location.X / (float)pictureBoxFundal.Width;
            Liechtensteiny = radioButtonLiechtenstein.Location.Y / (float)pictureBoxFundal.Height;
            Austriax = radioButtonAustria.Location.X / (float)pictureBoxFundal.Width;
            Austriay = radioButtonAustria.Location.Y / (float)pictureBoxFundal.Height;
            RepCehax = radioButtonRepCeha.Location.X / (float)pictureBoxFundal.Width;
            RepCehay = radioButtonRepCeha.Location.Y / (float)pictureBoxFundal.Height;
            Slovaciax = radioButtonSlovacia.Location.X / (float)pictureBoxFundal.Width;
            Slovaciay = radioButtonSlovacia.Location.Y / (float)pictureBoxFundal.Height;
            Ungariax = radioButtonUngaria.Location.X / (float)pictureBoxFundal.Width;
            Ungariay = radioButtonUngaria.Location.Y / (float)pictureBoxFundal.Height;
            Sloveniax = radioButtonSlovenia.Location.X / (float)pictureBoxFundal.Width;
            Sloveniay = radioButtonSlovenia.Location.Y / (float)pictureBoxFundal.Height;
            Croatiax = radioButtonCroatia.Location.X / (float)pictureBoxFundal.Width;
            Croatiay = radioButtonCroatia.Location.Y / (float)pictureBoxFundal.Height;
            Serbiax = radioButtonSerbia.Location.X / (float)pictureBoxFundal.Width;
            Serbiay = radioButtonSerbia.Location.Y / (float)pictureBoxFundal.Height;
            BosniaHx = radioButtonBosniaH.Location.X / (float)pictureBoxFundal.Width;
            BosniaHy = radioButtonBosniaH.Location.Y / (float)pictureBoxFundal.Height;
            Muntenegrux = radioButtonMuntenegru.Location.X / (float)pictureBoxFundal.Width;
            Muntenegruy = radioButtonMuntenegru.Location.Y / (float)pictureBoxFundal.Height;
            Albaniax = radioButtonAlbania.Location.X / (float)pictureBoxFundal.Width;
            Albaniay = radioButtonAlbania.Location.Y / (float)pictureBoxFundal.Height;
            Macedoniax = radioButtonMacedonia.Location.X / (float)pictureBoxFundal.Width;
            Macedoniay = radioButtonMacedonia.Location.Y / (float)pictureBoxFundal.Height;
            Kosovox = radioButtonKosovo.Location.X / (float)pictureBoxFundal.Width;
            Kosovoy = radioButtonKosovo.Location.Y / (float)pictureBoxFundal.Height;
            Greciax = radioButtonGrecia.Location.X / (float)pictureBoxFundal.Width;
            Greciay = radioButtonGrecia.Location.Y / (float)pictureBoxFundal.Height;
            Bulgariax = radioButtonBulgaria.Location.X / (float)pictureBoxFundal.Width;
            Bulgariay = radioButtonBulgaria.Location.Y / (float)pictureBoxFundal.Height;
            Moldovax = radioButtonMoldova.Location.X / (float)pictureBoxFundal.Width;
            Moldovay = radioButtonMoldova.Location.Y / (float)pictureBoxFundal.Height;
            Maltax = radioButtonMalta.Location.X / (float)pictureBoxFundal.Width;
            Maltay = radioButtonMalta.Location.Y / (float)pictureBoxFundal.Height;
            Ciprux = radioButtonCipru.Location.X / (float)pictureBoxFundal.Width;
            Cipruy = radioButtonCipru.Location.Y / (float)pictureBoxFundal.Height;
        }

        public Europa(string Cont, string Continent)
        {
            InitializeComponent();
            textBoxContinent.Text = Continent.ToString();
            textBoxCont.Text = Cont.ToString();
            fixareCoordonate();
        }

        void dimensiuneButoane()
        {
            radioButtonRomania.Size = new Size(10, 10);
            radioButtonIslanda.Size = new Size(10, 10);
            radioButtonNorvegia.Size = new Size(10, 10);
            radioButtonSuedia.Size = new Size(10, 10);
            radioButtonFinlanda.Size = new Size(10, 10);
            radioButtonRusia.Size = new Size(10, 10);
            radioButtonEstonia.Size = new Size(10, 10);
            radioButtonLetonia.Size = new Size(10, 10);
            radioButtonLituania.Size = new Size(10, 10);
            radioButtonIrlanda.Size = new Size(10, 10);
            radioButtonRegatulU.Size = new Size(10, 10);
            radioButtonBelarus.Size = new Size(10, 10);
            radioButtonUcraina.Size = new Size(10, 10);
            radioButtonMoldova.Size = new Size(10, 10);
            radioButtonPolonia.Size = new Size(10, 10);
            radioButtonGermania.Size = new Size(10, 10);
            radioButtonDanemarca.Size = new Size(10, 10);
            radioButtonFranta.Size = new Size(10, 10);
            radioButtonOlanda.Size = new Size(10, 10);
            radioButtonBelgia.Size = new Size(10, 10);
            radioButtonLuxemburg.Size = new Size(10, 10);
            radioButtonPortugalia.Size = new Size(10, 10);
            radioButtonSpania.Size = new Size(10, 10);
            radioButtonAndorra.Size = new Size(10, 10);
            radioButtonItalia.Size = new Size(10, 10);
            radioButtonMonaco.Size = new Size(10, 10);
            radioButtonVatican.Size = new Size(10, 10);
            radioButtonSanMarino.Size = new Size(10, 10);
            radioButtonElvetia.Size = new Size(10, 10);
            radioButtonAustria.Size = new Size(10, 10);
            radioButtonLiechtenstein.Size = new Size(10, 10);
            radioButtonRepCeha.Size = new Size(10, 10);
            radioButtonSlovacia.Size = new Size(10, 10);
            radioButtonUngaria.Size = new Size(10, 10);
            radioButtonSerbia.Size = new Size(10, 10);
            radioButtonSlovenia.Size = new Size(10, 10);
            radioButtonCroatia.Size = new Size(10, 10);
            radioButtonBosniaH.Size = new Size(10, 10);
            radioButtonMuntenegru.Size = new Size(10, 10);
            radioButtonKosovo.Size = new Size(10, 10);
            radioButtonAlbania.Size = new Size(10, 10);
            radioButtonMacedonia.Size = new Size(10, 10);
            radioButtonBulgaria.Size = new Size(10, 10);
            radioButtonGrecia.Size = new Size(10, 10);
            radioButtonCipru.Size = new Size(10, 10);
            radioButtonMalta.Size = new Size(10, 10);
        }

        void pozitieButoane()
        {
            radioButtonRomania.Location = new Point((int)(pictureBoxFundal.Width * Romaniax), (int)(pictureBoxFundal.Height * Romaniay));
            radioButtonMoldova.Location = new Point((int)(pictureBoxFundal.Width * Moldovax), (int)(pictureBoxFundal.Height * Moldovay));
            radioButtonUngaria.Location = new Point((int)(pictureBoxFundal.Width * Ungariax), (int)(pictureBoxFundal.Height * Ungariay));
            radioButtonSlovacia.Location = new Point((int)(pictureBoxFundal.Width * Slovaciax), (int)(pictureBoxFundal.Height * Slovaciay));
            radioButtonSlovenia.Location = new Point((int)(pictureBoxFundal.Width * Sloveniax), (int)(pictureBoxFundal.Height * Sloveniay) + 5);
            radioButtonRepCeha.Location = new Point((int)(pictureBoxFundal.Width * RepCehax), (int)(pictureBoxFundal.Height * RepCehay));
            radioButtonAustria.Location = new Point((int)(pictureBoxFundal.Width * Austriax), (int)(pictureBoxFundal.Height * Austriay));
            radioButtonCroatia.Location = new Point((int)(pictureBoxFundal.Width * Croatiax), (int)(pictureBoxFundal.Height * Croatiay));
            radioButtonElvetia.Location = new Point((int)(pictureBoxFundal.Width * Elvetiax), (int)(pictureBoxFundal.Height * Elvetiay));
            radioButtonLiechtenstein.Location = new Point((int)(pictureBoxFundal.Width * Liechtensteinx) + 3, (int)(pictureBoxFundal.Height * Liechtensteiny) + 3);
            radioButtonBosniaH.Location = new Point((int)(pictureBoxFundal.Width * BosniaHx), (int)(pictureBoxFundal.Height * BosniaHy));
            radioButtonSerbia.Location = new Point((int)(pictureBoxFundal.Width * Serbiax), (int)(pictureBoxFundal.Height * Serbiay));
            radioButtonKosovo.Location = new Point((int)(pictureBoxFundal.Width * Kosovox), (int)(pictureBoxFundal.Height * Kosovoy));
            radioButtonMuntenegru.Location = new Point((int)(pictureBoxFundal.Width * Muntenegrux) + 5, (int)(pictureBoxFundal.Height * Muntenegruy));
            radioButtonAlbania.Location = new Point((int)(pictureBoxFundal.Width * Albaniax), (int)(pictureBoxFundal.Height * Albaniay));
            radioButtonMacedonia.Location = new Point((int)(pictureBoxFundal.Width * Macedoniax), (int)(pictureBoxFundal.Height * Macedoniay));
            radioButtonBulgaria.Location = new Point((int)(pictureBoxFundal.Width * Bulgariax), (int)(pictureBoxFundal.Height * Bulgariay));
            radioButtonGrecia.Location = new Point((int)(pictureBoxFundal.Width * Greciax), (int)(pictureBoxFundal.Height * Greciay));
            radioButtonMalta.Location = new Point((int)(pictureBoxFundal.Width * Maltax) + 3, (int)(pictureBoxFundal.Height * Maltay) + 3);
            radioButtonCipru.Location = new Point((int)(pictureBoxFundal.Width * Ciprux), (int)(pictureBoxFundal.Height * Cipruy));
            radioButtonItalia.Location = new Point((int)(pictureBoxFundal.Width * Italiax), (int)(pictureBoxFundal.Height * Italiay));
            radioButtonSanMarino.Location = new Point((int)(pictureBoxFundal.Width * SanMarinox) + 3, (int)(pictureBoxFundal.Height * SanMarinoy) + 3);
            radioButtonVatican.Location = new Point((int)(pictureBoxFundal.Width * Vaticanx) + 3, (int)(pictureBoxFundal.Height * Vaticany) + 3);
            radioButtonMonaco.Location = new Point((int)(pictureBoxFundal.Width * Monacox) + 3, (int)(pictureBoxFundal.Height * Monacoy));
            radioButtonAndorra.Location = new Point((int)(pictureBoxFundal.Width * Andorrax) + 3, (int)(pictureBoxFundal.Height * Andorray) + 3);
            radioButtonPortugalia.Location = new Point((int)(pictureBoxFundal.Width * Portugaliax), (int)(pictureBoxFundal.Height * Portugaliay));
            radioButtonSpania.Location = new Point((int)(pictureBoxFundal.Width * Spaniax), (int)(pictureBoxFundal.Height * Spaniay));
            radioButtonFranta.Location = new Point((int)(pictureBoxFundal.Width * Frantax), (int)(pictureBoxFundal.Height * Frantay));
            radioButtonGermania.Location = new Point((int)(pictureBoxFundal.Width * Germaniax), (int)(pictureBoxFundal.Height * Germaniay));
            radioButtonPolonia.Location = new Point((int)(pictureBoxFundal.Width * Poloniax), (int)(pictureBoxFundal.Height * Poloniay));
            radioButtonLuxemburg.Location = new Point((int)(pictureBoxFundal.Width * Luxemburgx) + 7, (int)(pictureBoxFundal.Height * Luxemburgy) + 7);
            radioButtonBelgia.Location = new Point((int)(pictureBoxFundal.Width * Belgiax), (int)(pictureBoxFundal.Height * Belgiay));
            radioButtonOlanda.Location = new Point((int)(pictureBoxFundal.Width * Olandax), (int)(pictureBoxFundal.Height * Olanday));
            radioButtonDanemarca.Location = new Point((int)(pictureBoxFundal.Width * Danemarcax), (int)(pictureBoxFundal.Height * Danemarcay));
            radioButtonIrlanda.Location = new Point((int)(pictureBoxFundal.Width * Irlandax), (int)(pictureBoxFundal.Height * Irlanday));
            radioButtonRegatulU.Location = new Point((int)(pictureBoxFundal.Width * RegatulUx), (int)(pictureBoxFundal.Height * RegatulUy));
            radioButtonIslanda.Location = new Point((int)(pictureBoxFundal.Width * Islandax), (int)(pictureBoxFundal.Height * Islanday));
            radioButtonNorvegia.Location = new Point((int)(pictureBoxFundal.Width * Norvegiax), (int)(pictureBoxFundal.Height * Norvegiay));
            radioButtonSuedia.Location = new Point((int)(pictureBoxFundal.Width * Suediax), (int)(pictureBoxFundal.Height * Suediay));
            radioButtonFinlanda.Location = new Point((int)(pictureBoxFundal.Width * Finlandax), (int)(pictureBoxFundal.Height * Finlanday));
            radioButtonRusia.Location = new Point((int)(pictureBoxFundal.Width * Rusiax), (int)(pictureBoxFundal.Height * Rusiay));
            radioButtonUcraina.Location = new Point((int)(pictureBoxFundal.Width * Ucrainax), (int)(pictureBoxFundal.Height * Ucrainay));
            radioButtonBelarus.Location = new Point((int)(pictureBoxFundal.Width * Belarusx), (int)(pictureBoxFundal.Height * Belarusy));
            radioButtonEstonia.Location = new Point((int)(pictureBoxFundal.Width * Estoniax), (int)(pictureBoxFundal.Height * Estoniay));
            radioButtonLetonia.Location = new Point((int)(pictureBoxFundal.Width * Letoniax), (int)(pictureBoxFundal.Height * Letoniay));
            radioButtonLituania.Location = new Point((int)(pictureBoxFundal.Width * Lituaniax), (int)(pictureBoxFundal.Height * Lituaniay));
        }

        void ascunderePanele()
        {
            panelNorvegia.Hide();
            panelIslanda.Hide();
            panelSuedia.Hide();
            panelFinlanda.Hide();
            panelRusia.Hide();
            panelEstonia.Hide();
            panelLetonia.Hide();
            panelLituania.Hide();
            panelBelarus.Hide();
            panelUcraina.Hide();
            panelMoldova.Hide();
            panelRomania.Hide();
            panelCipru.Hide();
            panelMalta.Hide();
            panelPolonia.Hide();
            panelGrecia.Hide();
            panelMacedonia.Hide();
            panelBulgaria.Hide();
            panelAlbania.Hide();
            panelKosovo.Hide();
            panelMuntenegru.Hide();
            panelSerbia.Hide();
            panelBosniaH.Hide();
            panelCroatia.Hide();
            panelSlovenia.Hide();
            panelSlovacia.Hide();
            panelUngaria.Hide();
            panelAustria.Hide();
            panelRepCeha.Hide();
            panelDanemarca.Hide();
            panelGermania.Hide();
            panelLiechtenstein.Hide();
            panelItalia.Hide();
            panelSanMarino.Hide();
            panelMonaco.Hide();
            panelVatican.Hide();
            panelElvetia.Hide();
            panelLuxemburg.Hide();
            panelOlanda.Hide();
            panelIrlanda.Hide();
            panelRegatulU.Hide();
            panelFranta.Hide();
            panelAndorra.Hide();
            panelSpania.Hide();
            panelPortugalia.Hide();
            panelBelgia.Hide();
        }

        void dimensiunePanele()
        {
            panelNorvegia.Size = new Size(200, 200);
            panelIslanda.Size = new Size(200, 200);
            panelSuedia.Size = new Size(200, 200);
            panelFinlanda.Size = new Size(200, 200);
            panelRusia.Size = new Size(200, 200);
            panelEstonia.Size = new Size(200, 200);
            panelLetonia.Size = new Size(200, 200);
            panelLituania.Size = new Size(200, 200);
            panelBelarus.Size = new Size(200, 200);
            panelUcraina.Size = new Size(200, 200);
            panelMoldova.Size = new Size(200, 200);
            panelRomania.Size = new Size(200, 200);
            panelCipru.Size = new Size(200, 200);
            panelMalta.Size = new Size(200, 200);
            panelPolonia.Size = new Size(200, 200);
            panelGrecia.Size = new Size(200, 200);
            panelMacedonia.Size = new Size(200, 200);
            panelBulgaria.Size = new Size(200, 200);
            panelAlbania.Size = new Size(200, 200);
            panelKosovo.Size = new Size(200, 200);
            panelMuntenegru.Size = new Size(200, 200);
            panelSerbia.Size = new Size(200, 200);
            panelBosniaH.Size = new Size(200, 200);
            panelCroatia.Size = new Size(200, 200);
            panelSlovenia.Size = new Size(200, 200);
            panelSlovacia.Size = new Size(200, 200);
            panelUngaria.Size = new Size(200, 200);
            panelAustria.Size = new Size(200, 200);
            panelRepCeha.Size = new Size(200, 200);
            panelDanemarca.Size = new Size(200, 200);
            panelGermania.Size = new Size(200, 200);
            panelLiechtenstein.Size = new Size(200, 200);
            panelItalia.Size = new Size(200, 200);
            panelSanMarino.Size = new Size(200, 200);
            panelMonaco.Size = new Size(200, 200);
            panelVatican.Size = new Size(200, 200);
            panelElvetia.Size = new Size(200, 200);
            panelLuxemburg.Size = new Size(200, 200);
            panelOlanda.Size = new Size(200, 200);
            panelIrlanda.Size = new Size(200, 200);
            panelRegatulU.Size = new Size(200, 200);
            panelFranta.Size = new Size(200, 200);
            panelAndorra.Size = new Size(200, 200);
            panelSpania.Size = new Size(200, 200);
            panelPortugalia.Size = new Size(200, 200);
            panelBelgia.Size = new Size(200, 200);
        }

        void pozitiePanele()
        {
            panelRomania.Location = new Point(radioButtonRomania.Location.X + 30, radioButtonRomania.Location.Y - 30);
            panelNorvegia.Location = new Point(radioButtonNorvegia.Location.X + 30, radioButtonNorvegia.Location.Y - 30);
            panelIslanda.Location = new Point(radioButtonIslanda.Location.X + 30, radioButtonIslanda.Location.Y - 30);
            panelSuedia.Location = new Point(radioButtonSuedia.Location.X + 30, radioButtonSuedia.Location.Y - 30);
            panelFinlanda.Location = new Point(radioButtonFinlanda.Location.X + 30, radioButtonFinlanda.Location.Y - 30);
            panelRusia.Location = new Point(radioButtonRusia.Location.X - 230, radioButtonRusia.Location.Y - 30);
            panelEstonia.Location = new Point(radioButtonEstonia.Location.X + 30, radioButtonEstonia.Location.Y - 30);
            panelLetonia.Location = new Point(radioButtonLetonia.Location.X + 30, radioButtonLetonia.Location.Y - 30);
            panelLituania.Location = new Point(radioButtonLituania.Location.X + 30, radioButtonLituania.Location.Y - 30);
            panelBelarus.Location = new Point(radioButtonBelarus.Location.X + 30, radioButtonBelarus.Location.Y - 30);
            panelUcraina.Location = new Point(radioButtonUcraina.Location.X + 30, radioButtonUcraina.Location.Y - 30);
            panelMoldova.Location = new Point(radioButtonMoldova.Location.X + 30, radioButtonMoldova.Location.Y - 30);
            panelCipru.Location = new Point(radioButtonCipru.Location.X + 30, radioButtonCipru.Location.Y - 180);
            panelMalta.Location = new Point(radioButtonMalta.Location.X + 30, radioButtonMalta.Location.Y - 160);
            panelPolonia.Location = new Point(radioButtonPolonia.Location.X + 30, radioButtonPolonia.Location.Y - 30);
            panelGrecia.Location = new Point(radioButtonGrecia.Location.X + 30, radioButtonGrecia.Location.Y - 100);
            panelMacedonia.Location = new Point(radioButtonMacedonia.Location.X + 30, radioButtonMacedonia.Location.Y - 60);
            panelBulgaria.Location = new Point(radioButtonBulgaria.Location.X + 30, radioButtonBulgaria.Location.Y - 30);
            panelAlbania.Location = new Point(radioButtonAlbania.Location.X + 30, radioButtonAlbania.Location.Y - 70);
            panelKosovo.Location = new Point(radioButtonKosovo.Location.X + 30, radioButtonKosovo.Location.Y - 30);
            panelMuntenegru.Location = new Point(radioButtonMuntenegru.Location.X + 30, radioButtonMuntenegru.Location.Y - 30);
            panelSerbia.Location = new Point(radioButtonSerbia.Location.X + 30, radioButtonSerbia.Location.Y - 30);
            panelBosniaH.Location = new Point(radioButtonBosniaH.Location.X + 30, radioButtonBosniaH.Location.Y - 30);
            panelCroatia.Location = new Point(radioButtonCroatia.Location.X + 30, radioButtonCroatia.Location.Y - 30);
            panelSlovacia.Location = new Point(radioButtonSlovacia.Location.X + 30, radioButtonSlovacia.Location.Y - 30);
            panelSlovenia.Location = new Point(radioButtonSlovenia.Location.X + 30, radioButtonSlovenia.Location.Y - 30);
            panelUngaria.Location = new Point(radioButtonUngaria.Location.X + 30, radioButtonUngaria.Location.Y - 30);
            panelAustria.Location = new Point(radioButtonAustria.Location.X + 30, radioButtonAustria.Location.Y - 30);
            panelRepCeha.Location = new Point(radioButtonRepCeha.Location.X + 30, radioButtonRepCeha.Location.Y - 30);
            panelDanemarca.Location = new Point(radioButtonDanemarca.Location.X + 30, radioButtonDanemarca.Location.Y - 30);
            panelGermania.Location = new Point(radioButtonGermania.Location.X + 30, radioButtonGermania.Location.Y - 30);
            panelLiechtenstein.Location = new Point(radioButtonLiechtenstein.Location.X + 30, radioButtonLiechtenstein.Location.Y - 30);
            panelItalia.Location = new Point(radioButtonItalia.Location.X + 30, radioButtonItalia.Location.Y - 30);
            panelSanMarino.Location = new Point(radioButtonSanMarino.Location.X + 30, radioButtonSanMarino.Location.Y - 30);
            panelMonaco.Location = new Point(radioButtonMonaco.Location.X + 30, radioButtonMonaco.Location.Y - 30);
            panelVatican.Location = new Point(radioButtonVatican.Location.X + 30, radioButtonVatican.Location.Y - 40);
            panelElvetia.Location = new Point(radioButtonElvetia.Location.X + 30, radioButtonElvetia.Location.Y - 30);
            panelLuxemburg.Location = new Point(radioButtonLuxemburg.Location.X + 30, radioButtonLuxemburg.Location.Y - 30);
            panelOlanda.Location = new Point(radioButtonOlanda.Location.X + 30, radioButtonOlanda.Location.Y - 30);
            panelIrlanda.Location = new Point(radioButtonIrlanda.Location.X + 30, radioButtonIrlanda.Location.Y - 30);
            panelRegatulU.Location = new Point(radioButtonRegatulU.Location.X + 30, radioButtonRegatulU.Location.Y - 30);
            panelAndorra.Location = new Point(radioButtonAndorra.Location.X + 30, radioButtonAndorra.Location.Y - 80);
            panelSpania.Location = new Point(radioButtonSpania.Location.X + 30, radioButtonSpania.Location.Y - 80);
            panelPortugalia.Location = new Point(radioButtonPortugalia.Location.X + 30, radioButtonPortugalia.Location.Y - 80);
            panelFranta.Location = new Point(radioButtonFranta.Location.X + 30, radioButtonFranta.Location.Y - 30);
            panelBelgia.Location = new Point(radioButtonBelgia.Location.X + 30, radioButtonBelgia.Location.Y - 30);
        }

        void incarcarePoze()
        {
            pictureBoxAlbania.Dock = DockStyle.Fill;
            pictureBoxIslanda.Dock = DockStyle.Fill;
            pictureBoxNorvegia.Dock = DockStyle.Fill;
            pictureBoxSuedia.Dock = DockStyle.Fill;
            pictureBoxFinlanda.Dock = DockStyle.Fill;
            pictureBoxRusia.Dock = DockStyle.Fill;
            pictureBoxEstonia.Dock = DockStyle.Fill;
            pictureBoxLetonia.Dock = DockStyle.Fill;
            pictureBoxLituania.Dock = DockStyle.Fill;
            pictureBoxPolonia.Dock = DockStyle.Fill;
            pictureBoxBelarus.Dock = DockStyle.Fill;
            pictureBoxUcraina.Dock = DockStyle.Fill;
            pictureBoxRomania.Dock = DockStyle.Fill;
            pictureBoxIrlanda.Dock = DockStyle.Fill;
            pictureBoxRegatulU.Dock = DockStyle.Fill;
            pictureBoxDanemarca.Dock = DockStyle.Fill;
            pictureBoxGermania.Dock = DockStyle.Fill;
            pictureBoxOlanda.Dock = DockStyle.Fill;
            pictureBoxBelgia.Dock = DockStyle.Fill;
            pictureBoxUngaria.Dock = DockStyle.Fill;
            pictureBoxSlovacia.Dock = DockStyle.Fill;
            pictureBoxSlovenia.Dock = DockStyle.Fill;
            pictureBoxAustria.Dock = DockStyle.Fill;
            pictureBoxLuxemburg.Dock = DockStyle.Fill;
            pictureBoxLiechtenstein.Dock = DockStyle.Fill;
            pictureBoxFranta.Dock = DockStyle.Fill;
            pictureBoxMoldova.Dock = DockStyle.Fill;
            pictureBoxElvetia.Dock = DockStyle.Fill;
            pictureBoxPortugalia.Dock = DockStyle.Fill;
            pictureBoxSpania.Dock = DockStyle.Fill;
            pictureBoxAndorra.Dock = DockStyle.Fill;
            pictureBoxMalta.Dock = DockStyle.Fill;
            pictureBoxCipru.Dock = DockStyle.Fill;
            pictureBoxMonaco.Dock = DockStyle.Fill;
            pictureBoxBulgaria.Dock = DockStyle.Fill;
            pictureBoxItalia.Dock = DockStyle.Fill;
            pictureBoxSanMarino.Dock = DockStyle.Fill;
            pictureBoxVatican.Dock = DockStyle.Fill;
            pictureBoxSerbia.Dock = DockStyle.Fill;
            pictureBoxKosovo.Dock = DockStyle.Fill;
            pictureBoxBosniaH.Dock = DockStyle.Fill;
            pictureBoxCroatia.Dock = DockStyle.Fill;
            pictureBoxMuntenegru.Dock = DockStyle.Fill;
            pictureBoxGrecia.Dock = DockStyle.Fill;
            pictureBoxMacedonia.Dock = DockStyle.Fill;
            pictureBoxRepCeha.Dock = DockStyle.Fill;
            pictureBoxAlbania.Size = new Size(200,200);
        }

        private void Europa_Load(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Vizitator = 0;
            Agentie_de_turism.Properties.Settings.Default.Continent = 1;
            Agentie_de_turism.Properties.Settings.Default.Tara = 0;
            FormBorderStyle = FormBorderStyle.None;
            WindowState = FormWindowState.Maximized;
            textBoxContinent.Hide();
            textBoxTara.Hide();
            textBoxCont.Hide();
            dimensiuneButoane();
            pozitieButoane();
            ascunderePanele();
            dimensiunePanele();
            pozitiePanele();
            incarcarePoze();
        }

        private void buttonInapoi_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Vizitator = 1;
            Vizitator viz = new Vizitator(textBoxCont.Text);
            this.Close();
            viz.ShowDialog();
        }
    }
}
