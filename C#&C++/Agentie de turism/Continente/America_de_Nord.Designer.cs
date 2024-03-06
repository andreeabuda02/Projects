
namespace Agentie_de_turism
{
    partial class America_de_Nord
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(America_de_Nord));
            this.buttonDetaliiCont = new System.Windows.Forms.Button();
            this.textBoxCont = new System.Windows.Forms.TextBox();
            this.textBoxTara = new System.Windows.Forms.TextBox();
            this.textBoxContinent = new System.Windows.Forms.TextBox();
            this.buttonInapoi = new System.Windows.Forms.Button();
            this.pictureBoxFundal = new System.Windows.Forms.PictureBox();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBoxFundal)).BeginInit();
            this.SuspendLayout();
            // 
            // buttonDetaliiCont
            // 
            this.buttonDetaliiCont.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonDetaliiCont.BackColor = System.Drawing.Color.LightSkyBlue;
            this.buttonDetaliiCont.Location = new System.Drawing.Point(989, 0);
            this.buttonDetaliiCont.Margin = new System.Windows.Forms.Padding(4);
            this.buttonDetaliiCont.Name = "buttonDetaliiCont";
            this.buttonDetaliiCont.Size = new System.Drawing.Size(129, 27);
            this.buttonDetaliiCont.TabIndex = 111;
            this.buttonDetaliiCont.Text = "Account details";
            this.buttonDetaliiCont.UseVisualStyleBackColor = false;
            this.buttonDetaliiCont.Click += new System.EventHandler(this.buttonDetaliiCont_Click);
            // 
            // textBoxCont
            // 
            this.textBoxCont.Location = new System.Drawing.Point(0, 315);
            this.textBoxCont.Margin = new System.Windows.Forms.Padding(4);
            this.textBoxCont.Name = "textBoxCont";
            this.textBoxCont.Size = new System.Drawing.Size(132, 22);
            this.textBoxCont.TabIndex = 110;
            // 
            // textBoxTara
            // 
            this.textBoxTara.Location = new System.Drawing.Point(0, 375);
            this.textBoxTara.Margin = new System.Windows.Forms.Padding(4);
            this.textBoxTara.Name = "textBoxTara";
            this.textBoxTara.Size = new System.Drawing.Size(132, 22);
            this.textBoxTara.TabIndex = 109;
            // 
            // textBoxContinent
            // 
            this.textBoxContinent.Location = new System.Drawing.Point(0, 345);
            this.textBoxContinent.Margin = new System.Windows.Forms.Padding(4);
            this.textBoxContinent.Name = "textBoxContinent";
            this.textBoxContinent.Size = new System.Drawing.Size(132, 22);
            this.textBoxContinent.TabIndex = 108;
            // 
            // buttonInapoi
            // 
            this.buttonInapoi.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.buttonInapoi.BackColor = System.Drawing.Color.LightSkyBlue;
            this.buttonInapoi.Location = new System.Drawing.Point(13, 654);
            this.buttonInapoi.Margin = new System.Windows.Forms.Padding(4);
            this.buttonInapoi.Name = "buttonInapoi";
            this.buttonInapoi.Size = new System.Drawing.Size(104, 57);
            this.buttonInapoi.TabIndex = 107;
            this.buttonInapoi.Text = "Back";
            this.buttonInapoi.UseVisualStyleBackColor = false;
            this.buttonInapoi.Click += new System.EventHandler(this.buttonInapoi_Click);
            // 
            // pictureBoxFundal
            // 
            this.pictureBoxFundal.Dock = System.Windows.Forms.DockStyle.Fill;
            this.pictureBoxFundal.Image = ((System.Drawing.Image)(resources.GetObject("pictureBoxFundal.Image")));
            this.pictureBoxFundal.ImageLocation = "";
            this.pictureBoxFundal.Location = new System.Drawing.Point(0, 0);
            this.pictureBoxFundal.Margin = new System.Windows.Forms.Padding(4);
            this.pictureBoxFundal.Name = "pictureBoxFundal";
            this.pictureBoxFundal.Size = new System.Drawing.Size(1118, 724);
            this.pictureBoxFundal.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.pictureBoxFundal.TabIndex = 106;
            this.pictureBoxFundal.TabStop = false;
            // 
            // America_de_Nord
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1118, 724);
            this.Controls.Add(this.buttonDetaliiCont);
            this.Controls.Add(this.textBoxCont);
            this.Controls.Add(this.textBoxTara);
            this.Controls.Add(this.textBoxContinent);
            this.Controls.Add(this.buttonInapoi);
            this.Controls.Add(this.pictureBoxFundal);
            this.Name = "America_de_Nord";
            this.Text = "America_de_Nord";
            this.Load += new System.EventHandler(this.America_de_Nord_Load);
            ((System.ComponentModel.ISupportInitialize)(this.pictureBoxFundal)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button buttonDetaliiCont;
        private System.Windows.Forms.TextBox textBoxCont;
        private System.Windows.Forms.TextBox textBoxTara;
        private System.Windows.Forms.TextBox textBoxContinent;
        private System.Windows.Forms.Button buttonInapoi;
        private System.Windows.Forms.PictureBox pictureBoxFundal;
    }
}