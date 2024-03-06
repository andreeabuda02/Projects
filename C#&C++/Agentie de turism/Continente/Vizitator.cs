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
    public partial class Vizitator : Form
    {
        private float ANx, ANy;
        private float ASx, ASy;
        private float Afx, Afy;
        private float EUx, EUy;
        private float Asiax, Asiay;
        private float Aux, Auy;
        private float BCx, BCy;

        private void radioButtonAfrica_MouseEnter(object sender, EventArgs e)
        {
            radioButtonAfrica.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelAFInfo.Show();
        }

        private void radioButtonAfrica_MouseLeave(object sender, EventArgs e)
        {
            radioButtonAfrica.BackgroundImage = null;
        }

        private void radioButtonAmericaN_MouseEnter(object sender, EventArgs e)
        {
            radioButtonAmericaN.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelANInfo.Show();
        }

        private void radioButtonEuropa_MouseEnter(object sender, EventArgs e)
        {
            radioButtonEuropa.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelEUInfo.Show();
        }

        private void radioButtonEuropa_MouseLeave(object sender, EventArgs e)
        {
            radioButtonEuropa.BackgroundImage = null;
        }

        private void radioButtonAsia_MouseEnter(object sender, EventArgs e)
        {
            radioButtonAsia.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelASIAInfo.Show();
        }

        private void radioButtonAsia_MouseLeave(object sender, EventArgs e)
        {
            radioButtonAsia.BackgroundImage = null;
        }

        private void radioButtonAustralia_MouseEnter(object sender, EventArgs e)
        {
            radioButtonAustralia.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelAUInfo.Show();
        }

        private void radioButtonAustralia_MouseLeave(object sender, EventArgs e)
        {
            radioButtonAustralia.BackgroundImage = null;
        }

        private void radioButtonEuropa_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Vizitator = 0;
            Agentie_de_turism.Properties.Settings.Default.Continent = 1;
            textBoxContinent.Text = "Europe";
            Europa E = new Europa(textBoxCont.Text, textBoxContinent.Text);
            E.ShowDialog();
            this.Close();
        }

        private void radioButtonCloseASIA_Click(object sender, EventArgs e)
        {
            panelASIAInfo.Hide();
        }

        private void radioButtonCloseEU_Click(object sender, EventArgs e)
        {
            panelEUInfo.Hide();
        }

        private void radioButtonCloseAN_Click(object sender, EventArgs e)
        {
            panelANInfo.Hide();
        }

        private void radioButtonCloseAS_Click(object sender, EventArgs e)
        {
            panelASInfo.Hide();
        }

        private void radioButtonCloseAF_Click(object sender, EventArgs e)
        {
            panelAFInfo.Hide();
        }

        private void radioButtonCloseAU_Click(object sender, EventArgs e)
        {
            panelAUInfo.Hide();
        }

        private void radioButtonCloseASIA_MouseEnter(object sender, EventArgs e)
        {
            radioButtonCloseASIA.BackgroundImage = Agentie_de_turism.Properties.Resources.X;
        }

        private void radioButtonCloseEU_MouseEnter(object sender, EventArgs e)
        {
            radioButtonCloseEU.BackgroundImage = Agentie_de_turism.Properties.Resources.X;
        }

        private void radioButtonCloseAN_MouseEnter(object sender, EventArgs e)
        {
            radioButtonCloseAN.BackgroundImage = Agentie_de_turism.Properties.Resources.X;
        }

        private void radioButtonCloseAS_MouseEnter(object sender, EventArgs e)
        {
            radioButtonCloseAS.BackgroundImage = Agentie_de_turism.Properties.Resources.X;
        }

        private void radioButtonCloseAF_MouseEnter(object sender, EventArgs e)
        {
            radioButtonCloseAF.BackgroundImage = Agentie_de_turism.Properties.Resources.X;
        }

        private void radioButtonCloseAU_MouseEnter(object sender, EventArgs e)
        {
            radioButtonCloseAU.BackgroundImage = Agentie_de_turism.Properties.Resources.X;
        }

        private void radioButtonCloseAU_MouseLeave(object sender, EventArgs e)
        {
            radioButtonCloseAU.BackgroundImage = null;
        }

        private void radioButtonCloseAF_MouseLeave(object sender, EventArgs e)
        {
            radioButtonCloseAF.BackgroundImage = null;
        }

        private void radioButtonCloseAS_MouseLeave(object sender, EventArgs e)
        {
            radioButtonCloseAS.BackgroundImage = null;
        }

        private void radioButtonCloseAN_MouseLeave(object sender, EventArgs e)
        {
            radioButtonCloseAN.BackgroundImage = null;
        }

        private void radioButtonCloseEU_MouseLeave(object sender, EventArgs e)
        {
            radioButtonCloseEU.BackgroundImage = null;
        }

        private void buttonDetaliiCont_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Vizitator = 1;
            DetaliiCont dc = new DetaliiCont(textBoxCont.Text);
            dc.ShowDialog();
            this.Close();
        }

        private void radioButtonAmericaN_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Vizitator = 0;
            Agentie_de_turism.Properties.Settings.Default.Continent = 2;
            textBoxContinent.Text = "North America";
            America_de_Nord AmN = new America_de_Nord(textBoxCont.Text, textBoxContinent.Text);
            AmN.ShowDialog();
            this.Close();
        }

        private void radioButtonAsia_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Vizitator = 0;
            Agentie_de_turism.Properties.Settings.Default.Continent = 4;
            textBoxContinent.Text = "Asia";
            Asia As = new Asia(textBoxCont.Text, textBoxContinent.Text);
            As.ShowDialog();
            this.Close();
        }

        private void radioButtonAustralia_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Vizitator = 0;
            Agentie_de_turism.Properties.Settings.Default.Continent = 6;
            textBoxContinent.Text = "Australia";
            Australia Aus = new Australia(textBoxCont.Text, textBoxContinent.Text);
            Aus.ShowDialog();
            this.Close();
        }

        private void radioButtonAmericaS_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Vizitator = 0;
            Agentie_de_turism.Properties.Settings.Default.Continent = 3;
            textBoxContinent.Text = "South America";
            America_de_Sud AmS = new America_de_Sud(textBoxCont.Text, textBoxContinent.Text);
            AmS.ShowDialog();
            this.Close();
        }

        private void radioButtonAfrica_Click(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Vizitator = 0;
            Agentie_de_turism.Properties.Settings.Default.Continent = 5;
            textBoxContinent.Text = "Africa";
            Africa Af = new Africa(textBoxCont.Text, textBoxContinent.Text);
            Af.ShowDialog();
            this.Close();
        }

        private void radioButtonCloseASIA_MouseLeave(object sender, EventArgs e)
        {
            radioButtonCloseASIA.BackgroundImage = null;
        }

        private void radioButtonAmericaN_MouseLeave(object sender, EventArgs e)
        {
            radioButtonAmericaN.BackgroundImage = null;
        }

        private void radioButtonAmericaS_MouseEnter(object sender, EventArgs e)
        {
            radioButtonAmericaS.BackgroundImage = Agentie_de_turism.Properties.Resources.Bifa;
            panelASInfo.Show();
        }

        private void radioButtonAmericaS_MouseLeave(object sender, EventArgs e)
        {
            radioButtonAmericaS.BackgroundImage = null;
        }

        public Vizitator(string Cont)
        {
            InitializeComponent();
            textBoxCont.Text = Cont.ToString();
            ANx = radioButtonAmericaN.Location.X / (float)pictureBoxContinente.Width;
            ANy = radioButtonAmericaN.Location.Y / (float)pictureBoxContinente.Height;
            ASx = radioButtonAmericaS.Location.X / (float)pictureBoxContinente.Width;
            ASy = radioButtonAmericaS.Location.Y / (float)pictureBoxContinente.Height;
            Afx = radioButtonAfrica.Location.X / (float)pictureBoxContinente.Width;
            Afy = radioButtonAfrica.Location.Y / (float)pictureBoxContinente.Height;
            EUx = radioButtonEuropa.Location.X / (float)pictureBoxContinente.Width;
            EUy = radioButtonEuropa.Location.Y / (float)pictureBoxContinente.Height;
            Asiax = radioButtonAsia.Location.X / (float)pictureBoxContinente.Width;
            Asiay = radioButtonAsia.Location.Y / (float)pictureBoxContinente.Height;
            Aux = radioButtonAustralia.Location.X / (float)pictureBoxContinente.Width;
            Auy = radioButtonAustralia.Location.Y / (float)pictureBoxContinente.Height;
            BCx = buttonDetaliiCont.Location.X / (float)pictureBoxContinente.Width;
            BCy = buttonDetaliiCont.Location.Y / (float)pictureBoxContinente.Height;
        }

        private void Vizitator_Load(object sender, EventArgs e)
        {
            Agentie_de_turism.Properties.Settings.Default.Vizitator = 1;
            Agentie_de_turism.Properties.Settings.Default.Continent = 0;
            Agentie_de_turism.Properties.Settings.Default.Tara = 0;
            textBoxContinent.Hide();
            textBoxCont.Hide();
            panelEUInfo.Hide();
            panelASInfo.Hide();
            panelAFInfo.Hide();
            panelANInfo.Hide();
            panelASIAInfo.Hide();
            panelAUInfo.Hide();
            radioButtonAfrica.BackgroundImage = null;
            radioButtonAmericaN.BackgroundImage = null;
            radioButtonAmericaS.BackgroundImage = null;
            radioButtonEuropa.BackgroundImage = null;
            radioButtonAsia.BackgroundImage = null;
            radioButtonAustralia.BackgroundImage = null;
            radioButtonCloseASIA.BackgroundImage = null;
            radioButtonCloseAS.BackgroundImage = null;
            radioButtonCloseAN.BackgroundImage = null;
            radioButtonCloseAF.BackgroundImage = null;
            radioButtonCloseAU.BackgroundImage = null;
            radioButtonCloseEU.BackgroundImage = null;
            FormBorderStyle = FormBorderStyle.None;
            WindowState = FormWindowState.Maximized;
            radioButtonAmericaN.Size = new Size(15, 15);
            radioButtonAmericaN.Location = new Point((int)(pictureBoxContinente.Width*ANx),(int)(pictureBoxContinente.Height*ANy));
            radioButtonAmericaS.Size = new Size(15, 15);
            radioButtonAmericaS.Location = new Point((int)(pictureBoxContinente.Width * ASx), (int)(pictureBoxContinente.Height * ASy));
            radioButtonAfrica.Size = new Size(15, 15);
            radioButtonAfrica.Location = new Point((int)(pictureBoxContinente.Width * Afx), (int)(pictureBoxContinente.Height * Afy));
            radioButtonEuropa.Size = new Size(15, 15);
            radioButtonEuropa.Location = new Point((int)(pictureBoxContinente.Width * EUx), (int)(pictureBoxContinente.Height * EUy));
            radioButtonAsia.Size = new Size(15, 15);
            radioButtonAsia.Location = new Point((int)(pictureBoxContinente.Width * Asiax), (int)(pictureBoxContinente.Height * Asiay));
            radioButtonAustralia.Size = new Size(15, 15);
            radioButtonAustralia.Location = new Point((int)(pictureBoxContinente.Width * Aux), (int)(pictureBoxContinente.Height * Auy));
            panelEUInfo.Location = new Point(radioButtonEuropa.Location.X + 80,radioButtonEuropa.Location.Y - 50);
            panelEUInfo.Size = new Size(400,600);
            panelASIAInfo.Location = new Point(radioButtonAsia.Location.X - 450, radioButtonAsia.Location.Y - 50);
            panelASIAInfo.Size = new Size(400, 600);
            panelANInfo.Location = new Point(radioButtonAmericaN.Location.X + 80, radioButtonAmericaN.Location.Y - 120);
            panelANInfo.Size = new Size(400, 600);
            panelASInfo.Location = new Point(radioButtonAmericaS.Location.X + 80, radioButtonAmericaS.Location.Y - 410);
            panelASInfo.Size = new Size(400, 600);
            panelAFInfo.Location = new Point(radioButtonAfrica.Location.X + 80, radioButtonAfrica.Location.Y - 260);
            panelAFInfo.Size = new Size(400, 600);
            panelAUInfo.Location = new Point(radioButtonAustralia.Location.X - 450, radioButtonAustralia.Location.Y - 440);
            panelAUInfo.Size = new Size(400, 600);
            pictureBox1.Size = new Size(400, 350);
            pictureBox2.Size = new Size(400, 350);
            pictureBox3.Size = new Size(400, 350);
            pictureBox4.Size = new Size(400, 350);
            pictureBox5.Size = new Size(400, 350);
            pictureBox6.Size = new Size(400, 350);
            radioButtonCloseAN.Location = new Point(380,0);
            radioButtonCloseAS.Location = new Point(380, 0);
            radioButtonCloseEU.Location = new Point(380, 0);
            radioButtonCloseAF.Location = new Point(380, 0);
            radioButtonCloseASIA.Location = new Point(380, 0);
            radioButtonCloseAU.Location = new Point(380, 0);
            textBoxInfoEU.Multiline = true;
            textBoxInfoEU.ScrollBars = ScrollBars.Vertical;
            textBoxInfoEU.AcceptsReturn = true;
            textBoxInfoEU.AcceptsTab = true;
            textBoxInfoEU.Size = new Size(400, 250);
            textBoxInfoAN.Multiline = true;
            textBoxInfoAN.ScrollBars = ScrollBars.Vertical;
            textBoxInfoAN.AcceptsReturn = true;
            textBoxInfoAN.AcceptsTab = true;
            textBoxInfoAN.Size = new Size(400, 250);
            textBoxInfoAS.Multiline = true;
            textBoxInfoAS.ScrollBars = ScrollBars.Vertical;
            textBoxInfoAS.AcceptsReturn = true;
            textBoxInfoAS.AcceptsTab = true;
            textBoxInfoAS.Size = new Size(400, 250);
            textBoxInfoAF.Multiline = true;
            textBoxInfoAF.ScrollBars = ScrollBars.Vertical;
            textBoxInfoAF.AcceptsReturn = true;
            textBoxInfoAF.AcceptsTab = true;
            textBoxInfoAF.Size = new Size(400, 250);
            textBoxInfoAU.Multiline = true;
            textBoxInfoAU.ScrollBars = ScrollBars.Vertical;
            textBoxInfoAU.AcceptsReturn = true;
            textBoxInfoAU.AcceptsTab = true;
            textBoxInfoAU.Size = new Size(400, 250);
            textBoxInfoASIA.Multiline = true;
            textBoxInfoASIA.ScrollBars = ScrollBars.Vertical;
            textBoxInfoASIA.AcceptsReturn = true;
            textBoxInfoASIA.AcceptsTab = true;
            textBoxInfoASIA.Size = new Size(400, 250);
            textBoxInfoEU.Text = "          There simply is no way to tour Europe and not be awestruck by its natural beauty, epic history and dazzling artistic and culinary diversity." +
                "                                               *Cultural Heritage:                            Europe’s almost unmanageable wealth of attractions is its biggest single draw: the birthplace of democracy in Athens, the Renaissance art of Florence, the graceful canals of Venice, the Napoleonic splendour of Paris, and the multilayered historical and cultural canvas of London. Less obvious but no less impressive attractions include Moorish palaces in Andalucía, the fascinating East-meets-West brew of İstanbul in Turkey, the majesty of meticulously restored imperial palaces in Russia's former capital St Petersburg and the ongoing project of Gaudí's La Sagrada Família in Barcelona." +
                "                  *Glorious Scenery:                               Despite its population density Europe maintains spectacular natural scenery: rugged Scottish Highlands with glens and lochs; Norway's fabulous fjords, seemingly chipped to jagged perfection by giants; the vine-raked valleys of the Loire; and the steppe-like plains of central Spain. For beaches, take a circuit of the Mediterranean's northern coast where beach holidays were practically invented. Or strike out to lesser-known yet beautiful coastal regions such as the Baltic and Black Seas. Mountain lovers should head to the Alps: they march across central Europe taking in France, Switzerland, Austria, northern Italy and tiny Liechtenstein." +
                "                    *Raise a Glass:                                    Cheers! Salud! Prost! Cin cin! À votre santé! Europe has some of the best nightlife in the world. Globally famous DJs keep the party going in London, Berlin and Paris, all of which also offer top-class entertainment, especially theatre and live music. Other key locations for high-energy nightlife include Moscow, Belgrade, Budapest and Madrid, while those hankering for something cosier can add Dublin's pubs or Vienna's cafes to their itinerary. Continue to party on the continent's streets at a multiplicity of festivals, from city parades attended by thousands to concerts in an ancient amphitheatre." +
                "                                           *Magnificent Menus:                             Once you've ticked off the great museums, panoramic vistas and energetic nightlife, what's left? A chance to indulge in a culinary adventure to beat all others. Who wouldn't want to snack on pizza in Naples, souvlaki in Santorini or even haggis in Scotland? But did you also know that Britain has some of the best Indian restaurants in the world; that Turkey's doner kebab is a key part of contemporary German food culture; and that in the Netherlands you can gorge on an Indonesian rijsttafel (rice table)? Europe's diversity and global reach is its trump card.";
            textBoxInfoAN.Text = "          Rolling the image of the North American continent as a huge tapestry of cultural and natural experiences, from southern Mexico, through the United States and northern Canada, it would take more lives to assimilate all the textures that America North offers them. North America is home to two of the youngest, richest and most powerful nations on earth, as well as the birthplace of one of the oldest civilizations.The heart of North America beats through towering forests, undulating fields, high-plain deserts, pulsating metropolises and offbeat oases." +
                "                                  *Culture:                                                  Iconic cities that need no introduction are just the icing on this culture-laden cake. Yes, you have the Museum of Modern Art in New York and the Smithsonian in Washington DC but the buzz of music, art and film finds its way down into everyday life, with citizens as often creating as much as consuming. A historical melting pot of cultures and identities, North America features some of the world’s most multicultural art. From Toronto's film festival to Mexico City's thriving music scene, North America is a veritable smorgasbord of enlightening experiences just waiting to be uncovered." +
                "        *Landscapes:                                       Even the most hardcore North American urban and suburbanites are forced to stop and gawp when confronted with the sheer natural beauty that is their homeland. From red-rock deserts to lush tropical rainforests, North America has the rare claim of covering every climatic zone, and its deepest gorge in Mexico’s Copper Canyon and Mt McKinley in Alaska exceed geographical extremes. Whether you're relaxing on a virtually undiscovered beach, racing down the slopes of the Great White North or scaling the iconic crags of the Grand Canyon – North America is certain to take your breath away." +
                "                           *Adventure:                                             In this land, adventure is king. Venture on a Canadian wilderness trek, buckle up for the legendary road trip along Route 66 or explore ancient rites at mysterious Maya and Aztec ruins. Whatever your travel dreams, North America offers a kaleidoscope of cultures, cuisines, landscapes, history and adventures that are bound to fulfill." +
                "        *Food:                                                     On one evening across North America, thick barbecue ribs and smoked brisket come piping hot at a Texas roadhouse, while talented chefs blend organic produce with Asian accents at award-winning West Coast restaurants. Locals get their fix of simple street tacos in Mexico, and a continent away, golden fries disappear under a steaming pile of gravy and cheese curds in a plate of poutine. Fresh lobster served off a Maine pier, oysters and champagne in a Vancouver wine bar, beer and pizza at a Midwestern pub – these are just a few ways to dine à la Americana.";
            textBoxInfoAS.Text = "          Andean peaks, Amazonian rainforest, Patagonian glaciers, Incan ruins, colonial towns, white-sand beaches and vertiginous nightlife: the wonders of South America set the stage for incredible adventures." +
                "               *Setting for Big Adventures:                       You can hike past ancient temples first laid down by the Incas, contemplate the awe-inspiring power of Iguazú Falls, or spend the day watching wildlife from a dugout canoe on one of the Amazon's countless igarapés (narrow waterways). You can barrel down Andean roads by mountain bike, go white-water rafting on Class V rivers and surf amazing breaks off both coasts. And once you think you've experienced it all, head to the dramatic landscapes in Tierra del Fuego, go eye-to-eye with extraordinary creatures in the Galápagos, and scramble up tableland mountains in the Gran Sabana for a panorama that seems straight out of the Mesozoic era." +
                "                                   *Cultural Treasures:                                South America's diversity doesn't end with landscapes. You'll find colonial towns where cobblestone streets lead past gilded churches and stately plazas little changed since the 18th century. You can haggle over colorful textiles at indigenous markets, share meals with traditional dwellers of the rainforest and follow the pounding rhythms of Afro-Brazilian drum corps. South America is home to an astounding variety of living and ancient cultures, and experiencing it first-hand is as easy as showing up." +
                "                                   *La Vida Musical:                                       This is one of the world's great music destinations. Nothing compares to hearing the rhythms of Colombian salsa, Brazilian samba, Argentine tango and Andean folk music in the place where they were born. Buenos Aires' sultry milongas (tango clubs), Rio's simmering garrafeiras (dance halls), Quito's salsotecas (salsa clubs) – all great places to chase the heart of Saturday night. Yet this is only the beginning of a great musical odyssey that encompasses Peruvian trovas, soulful Ecuadorian passillos, fast-stepping Brazilian forró, whirling Venezuelan merengue, steel-pan Guyanese drumming, Paraguayan harp music and more. Simply plunge in – though you might want to take a dance class along the way!" +
                "                                                   *Captivating Landscapes:                             From the snow - capped peaks of the Andes to the undulating waterways of the Amazon, South America spreads a dazzling array of natural wonders.This is a continent of lush rainforests, towering volcanoes, misty cloud forests, bone-dry deserts, red - rock canyons and ice - blue glaciers.After taking in some of the incredible natural wonders found in every country in South America, you can head to the coast for an idyllic retreat among palm - fringed, white - sand beaches and photogenic tropical islands.As landscapes go, there aren't many other places on earth that offer so much variety.";
            textBoxInfoASIA.Text = "            From the nomadic steppes of Kazakhstan to the frenetic streets of Hanoi, Asia is a continent so full of intrigue, adventure, solace and spirituality that it has fixated and confounded travellers for centuries." +
                "                                                  *Ancient Civilisations:                              This continent has contributed a cast of villains and heroes to global history. Most of the significant achievements of the modern world had their infancy in Asia. Historic trading routes sliced across epic terrain as expanding empires competed to trade goods and ideas throughout the continent and beyond. Asia’s ambitious civilisations ultimately gave rise to some of the world’s most revolutionary ideas and important technology. Ancient wonders and sacred spaces abound across the continent, from the Great Wall of China and the temples of Angkor to lesser-known marvels in Myanmar, Nepal and Afghanistan." +
                "                           *Vast Landscapes:                                   From sublime coastlines to snow-capped mountains, the majestic Mekong River to wildlife infested jungle, Asian landscapes hold an immediacy and vibrancy that captivates and enchants. Immense expanses of desert flow down from inhospitable mountains, which in turn give way to seemingly impenetrable forests. In a land where tigers still roam free (though far from noisy tourists) nature continues to be the driving force in many peoples’ lives. Virtually every climate on the globe is represented here; take a trek over the Gobi’s arching dunes or sun yourself on the sand-fringed tropical islands of the South China Sea." +
                "                               *Food to Live By:                                       Is there any greater place to eat than Asia? The continent has exported its cuisines the world over: India’s red hot curries, China’s juicy dumplings, Vietnam’s steaming bowls of pho soup and Thailand’s heaping plates of pàt tai (pad Thai) noodles are known and loved across the globe. Eating here can be both a joyous and chaotic affair: forks are forsaken in favour of fingers or chopsticks and food is enjoyed with unrivalled gusto. Whether settling down for a Michelin-starred meal in one of Singapore’s finest restaurants or pulling up a plastic stool on a Bangkok street, hungry travellers will never be bored by the diversity of Asia’s cuisines." +
                "                           *Glimpse of the Future:                               Gleaming skyscrapers, whooshing magnetic trains, shiny smartphones: in Asia, the future is now. China is charging its way into the 21st century with its economy developing at a head-spinning pace, while South Korea boasts some of the fastest internet speeds in the world and India is a hub of growing technology. A frenetic buzz surrounds urban Asia: the fashion, culture and business in the continent’s metropolises easily challenge the biggest European and American cities for their status as global hubs. This ever-evolving modernity can make for some incredibly special travel experiences: watch rice paddies flash by from a high-speed train, pick up a shiny new laptop in a Hong Kong electronics market or go to a robot cabaret show in Japan.";
            textBoxInfoAF.Text = "          Africa. There's nowhere like it on the planet for wildlife, wild lands and rich traditions that endure. Prepare to fall in love." +
                " *Natural Beauty:                                       Whether you're a wide-eyed first-timer or a frequent visitor, Africa cannot fail to get under your skin. The canvas upon which the continent's epic story is written is itself astonishing, and reason enough to visit. From the tropical rainforests and glorious tropical coastline of Central Africa to the rippling dunes of the Namib Desert, from the signature savannah of the Serengeti to jagged mountains, green-tinged highlands and deep-gash canyons that mark the Great Rift Valley's continental traverse – wherever you find yourself on this big, beautiful continent, Africa has few peers when it comes to natural beauty." +
                "                                               *New Africa:                                            The past retains its hold over the lives of many Africans, but just as many have embraced the future, bringing creativity and sophistication to the continent's cities and urban centres. Sometimes this New Africa is expressed in a creative-conservation search for solutions to the continent's environmental problems, or in an eagerness to break free of the restrictive chains of the past and transform the traveller experience. But just as often, modern Africans are taking all that is new and fusing it onto the best of the old." +
                "      *Ancient Africa:                                         On this continent where human beings first came into existence, customs, traditions and ancient rites tie Africans to generations and ancestors past and to the collective memory of myriad people. In many rural areas it can feel as though the modern world might never have happened, and they are all the better for it, and old ways of doing things – with a certain grace and civility, hospitality and a community spirit – survive. There are time-honoured ceremonies, music that dates back to the days of Africa's golden empires, and masks that tell stories of spirit worlds never lost. Welcome to Old Africa." +
                "                         *Wildlife Bonanza:                                      A Noah's ark of wildlife brings Africa's landscapes to life, with a tangible and sometimes profoundly mysterious presence that adds so much personality to the African wild. So many of the great beasts, including elephants, hippos and lions, call Africa home. Going on safari may be something of a travel cliché, but we're yet to find a traveller who has watched the wildlife world in motion in the Masai Mara, watched the epic battles between predator and prey in the Okavango Delta, or communed with gorillas and surfing hippos in Gabon and has not been reduced to an ecstatic state of childlike wonder.";
            textBoxInfoAU.Text = "          Australia is the unexpected: a place where the world’s oldest cultures share vast ochre plains, stylish laneways and unimaginably blue waters with successive waves of new arrivals from across the globe." +
                "    *An Ancient Land:                                    Australia is a country, or Australia is the name for a land that encompasses many countries; to understand the latter is to walk in the footsteps of the first peoples. Whether you’re tracing outlines of rock art more than 20,000 years old in Kakadu National Park, floating in the azure waters of Rottnest Island or admiring the iconic sites of Sydney Harbour where the Eora Nation traded for centuries, you are on Indigenous land." +
                "           *Urban Wonder:                                      Nowhere builds cities quite like Australia: each is a homage to magnificent waterways or beachfronts, while offering different experiences across different geographies. Grab a bicycle from one of Melbourne’s bike-share racks and tour the city’s fashion districts and cafe-lined laneways. Only a city like Darwin can fuse southern-Asian influence with contemporary Aboriginal culture (and leave you with an impressive sunburn to boot). Want a bit of everything? Sydney will take your breath away with its natural beauty and bustling neighbourhoods, while Hobart strikes a chord with its Gothic history and contemporary art." +
                "                                   *Adventurous Spirit:                                   You only have to travel a stone’s throw from any of Australia’s capital cities before you’ve landed somewhere truly out of this world. Not scared of the deep blue? Dive into famous reefs from the Ningaloo to the Great Barrier Reef, or witness majestic southern right whales along the Great Australian Bight. Like thrills? Head to the incredible wildlife parks outside of Brisbane (Australia Zoo) and Darwin (Crocodylus Park). And nothing will steady your sea legs more than getting on a 4WD tour and hitting one of the many dirt roads leading to rocky outcrops, from Uluru to the Kimberley." +
                "                                    *A Foodie’s Dream:                            Decades of migration combined with the re-emergence of native ingredients has brought Australian cuisine on to the radar of the world’s best chefs. You can buy a mouth-watering kangaroo steak complemented by indigenous greens at high-end restaurants, or take a bush tucker tour outside Alice Springs and learn first-hand which local plants to taste. No trip to Tasmania would be complete without planning exactly where you’ll slurp freshly shucked oysters, and don’t leave South Australia without a Barossa Valley taste tour. And a word for the brave: Darwinians love their spice!";
            textBoxInfoEU.Location = new Point(pictureBox1.Location.X, pictureBox1.Location.Y + 350);
            textBoxInfoAN.Location = new Point(pictureBox2.Location.X, pictureBox2.Location.Y + 350);
            textBoxInfoAS.Location = new Point(pictureBox3.Location.X, pictureBox3.Location.Y + 350);
            textBoxInfoAF.Location = new Point(pictureBox4.Location.X, pictureBox4.Location.Y + 350);
            textBoxInfoAU.Location = new Point(pictureBox6.Location.X, pictureBox6.Location.Y + 350);
            textBoxInfoASIA.Location = new Point(pictureBox5.Location.X, pictureBox5.Location.Y + 350);
        }

        private void buttonInapoi_Click(object sender, EventArgs e)
        {
            var rez = MessageBox.Show("Are you sure? You will be logged out if you exit.", "Reminder", MessageBoxButtons.YesNo, MessageBoxIcon.Question);
            if (rez == DialogResult.Yes)
            {
                Form1 f = new Form1();
                this.Close();
                f.ShowDialog();
            }
        }
    }
}
