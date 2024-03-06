
namespace Agentie_de_turism
{
    partial class Form1
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
            this.panelMeniu = new System.Windows.Forms.Panel();
            this.buttonIesire = new System.Windows.Forms.Button();
            this.buttonVizitator = new System.Windows.Forms.Button();
            this.buttonAdmin = new System.Windows.Forms.Button();
            this.pictureBoxFundal = new System.Windows.Forms.PictureBox();
            this.panelAdmin = new System.Windows.Forms.Panel();
            this.buttonLogare = new System.Windows.Forms.Button();
            this.textBoxParola = new System.Windows.Forms.TextBox();
            this.labelParola = new System.Windows.Forms.Label();
            this.buttonInapoi = new System.Windows.Forms.Button();
            this.panelMeniu.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBoxFundal)).BeginInit();
            this.panelAdmin.SuspendLayout();
            this.SuspendLayout();
            // 
            // panelMeniu
            // 
            this.panelMeniu.Controls.Add(this.buttonIesire);
            this.panelMeniu.Controls.Add(this.buttonVizitator);
            this.panelMeniu.Controls.Add(this.buttonAdmin);
            this.panelMeniu.Controls.Add(this.pictureBoxFundal);
            this.panelMeniu.Location = new System.Drawing.Point(200, 15);
            this.panelMeniu.Margin = new System.Windows.Forms.Padding(4);
            this.panelMeniu.Name = "panelMeniu";
            this.panelMeniu.Size = new System.Drawing.Size(851, 449);
            this.panelMeniu.TabIndex = 0;
            // 
            // buttonIesire
            // 
            this.buttonIesire.BackColor = System.Drawing.Color.Tan;
            this.buttonIesire.Font = new System.Drawing.Font("Microsoft Sans Serif", 20F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonIesire.Location = new System.Drawing.Point(405, 377);
            this.buttonIesire.Margin = new System.Windows.Forms.Padding(4);
            this.buttonIesire.Name = "buttonIesire";
            this.buttonIesire.Size = new System.Drawing.Size(123, 69);
            this.buttonIesire.TabIndex = 3;
            this.buttonIesire.Text = "Exit";
            this.buttonIesire.UseVisualStyleBackColor = false;
            this.buttonIesire.Click += new System.EventHandler(this.buttonIesire_Click);
            // 
            // buttonVizitator
            // 
            this.buttonVizitator.BackColor = System.Drawing.Color.SkyBlue;
            this.buttonVizitator.Font = new System.Drawing.Font("Microsoft YaHei UI", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonVizitator.Image = global::Agentie_de_turism.Properties.Resources.download;
            this.buttonVizitator.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.buttonVizitator.Location = new System.Drawing.Point(480, 112);
            this.buttonVizitator.Margin = new System.Windows.Forms.Padding(4);
            this.buttonVizitator.Name = "buttonVizitator";
            this.buttonVizitator.Size = new System.Drawing.Size(171, 79);
            this.buttonVizitator.TabIndex = 2;
            this.buttonVizitator.Text = "Guest";
            this.buttonVizitator.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.buttonVizitator.UseVisualStyleBackColor = false;
            this.buttonVizitator.Click += new System.EventHandler(this.buttonVizitator_Click);
            // 
            // buttonAdmin
            // 
            this.buttonAdmin.BackColor = System.Drawing.Color.SkyBlue;
            this.buttonAdmin.Font = new System.Drawing.Font("Microsoft YaHei UI", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonAdmin.Image = global::Agentie_de_turism.Properties.Resources.generic_user_icon_4___Copy__2_1;
            this.buttonAdmin.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.buttonAdmin.Location = new System.Drawing.Point(267, 112);
            this.buttonAdmin.Margin = new System.Windows.Forms.Padding(4);
            this.buttonAdmin.Name = "buttonAdmin";
            this.buttonAdmin.Size = new System.Drawing.Size(171, 79);
            this.buttonAdmin.TabIndex = 1;
            this.buttonAdmin.Text = "Admin";
            this.buttonAdmin.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.buttonAdmin.UseVisualStyleBackColor = false;
            this.buttonAdmin.Click += new System.EventHandler(this.buttonAdmin_Click);
            // 
            // pictureBoxFundal
            // 
            this.pictureBoxFundal.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.pictureBoxFundal.Image = global::Agentie_de_turism.Properties.Resources._20171017083023_jpg_800x600_q85_crop_center;
            this.pictureBoxFundal.ImageLocation = "";
            this.pictureBoxFundal.Location = new System.Drawing.Point(16, 15);
            this.pictureBoxFundal.Margin = new System.Windows.Forms.Padding(4);
            this.pictureBoxFundal.Name = "pictureBoxFundal";
            this.pictureBoxFundal.Size = new System.Drawing.Size(1035, 516);
            this.pictureBoxFundal.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.pictureBoxFundal.TabIndex = 0;
            this.pictureBoxFundal.TabStop = false;
            // 
            // panelAdmin
            // 
            this.panelAdmin.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.panelAdmin.Controls.Add(this.buttonLogare);
            this.panelAdmin.Controls.Add(this.textBoxParola);
            this.panelAdmin.Controls.Add(this.labelParola);
            this.panelAdmin.Controls.Add(this.buttonInapoi);
            this.panelAdmin.Location = new System.Drawing.Point(29, 50);
            this.panelAdmin.Margin = new System.Windows.Forms.Padding(4);
            this.panelAdmin.Name = "panelAdmin";
            this.panelAdmin.Size = new System.Drawing.Size(764, 388);
            this.panelAdmin.TabIndex = 3;
            this.panelAdmin.Paint += new System.Windows.Forms.PaintEventHandler(this.panelAdmin_Paint);
            // 
            // buttonLogare
            // 
            this.buttonLogare.BackColor = System.Drawing.Color.Indigo;
            this.buttonLogare.Font = new System.Drawing.Font("Microsoft Sans Serif", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonLogare.Location = new System.Drawing.Point(419, 238);
            this.buttonLogare.Margin = new System.Windows.Forms.Padding(4);
            this.buttonLogare.Name = "buttonLogare";
            this.buttonLogare.Size = new System.Drawing.Size(133, 69);
            this.buttonLogare.TabIndex = 3;
            this.buttonLogare.Text = "Login";
            this.buttonLogare.UseVisualStyleBackColor = false;
            this.buttonLogare.Click += new System.EventHandler(this.buttonLogare_Click);
            // 
            // textBoxParola
            // 
            this.textBoxParola.Location = new System.Drawing.Point(309, 187);
            this.textBoxParola.Margin = new System.Windows.Forms.Padding(4);
            this.textBoxParola.Name = "textBoxParola";
            this.textBoxParola.PasswordChar = '*';
            this.textBoxParola.Size = new System.Drawing.Size(155, 22);
            this.textBoxParola.TabIndex = 2;
            this.textBoxParola.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.textBoxParola_KeyPress);
            // 
            // labelParola
            // 
            this.labelParola.AutoSize = true;
            this.labelParola.BackColor = System.Drawing.Color.MediumOrchid;
            this.labelParola.Font = new System.Drawing.Font("Microsoft Sans Serif", 18F, System.Drawing.FontStyle.Bold);
            this.labelParola.Location = new System.Drawing.Point(146, 174);
            this.labelParola.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.labelParola.Name = "labelParola";
            this.labelParola.Size = new System.Drawing.Size(164, 36);
            this.labelParola.TabIndex = 1;
            this.labelParola.Text = "Password:";
            // 
            // buttonInapoi
            // 
            this.buttonInapoi.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.buttonInapoi.BackColor = System.Drawing.Color.Indigo;
            this.buttonInapoi.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonInapoi.Location = new System.Drawing.Point(4, 302);
            this.buttonInapoi.Margin = new System.Windows.Forms.Padding(4);
            this.buttonInapoi.Name = "buttonInapoi";
            this.buttonInapoi.Size = new System.Drawing.Size(140, 82);
            this.buttonInapoi.TabIndex = 0;
            this.buttonInapoi.Text = "Back";
            this.buttonInapoi.UseVisualStyleBackColor = false;
            this.buttonInapoi.Click += new System.EventHandler(this.buttonInapoi_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1067, 530);
            this.Controls.Add(this.panelAdmin);
            this.Controls.Add(this.panelMeniu);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.Margin = new System.Windows.Forms.Padding(4);
            this.Name = "Form1";
            this.Text = "Agentie Turism";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.panelMeniu.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.pictureBoxFundal)).EndInit();
            this.panelAdmin.ResumeLayout(false);
            this.panelAdmin.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel panelMeniu;
        private System.Windows.Forms.Button buttonVizitator;
        private System.Windows.Forms.Button buttonAdmin;
        private System.Windows.Forms.PictureBox pictureBoxFundal;
        private System.Windows.Forms.Panel panelAdmin;
        private System.Windows.Forms.Button buttonInapoi;
        private System.Windows.Forms.Label labelParola;
        private System.Windows.Forms.Button buttonLogare;
        private System.Windows.Forms.TextBox textBoxParola;
        private System.Windows.Forms.Button buttonIesire;
    }
}

