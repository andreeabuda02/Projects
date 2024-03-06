library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity Control_Unit is
  Port ( 
        clock : in STD_LOGIC;
        reset : in STD_LOGIC;
        buton : in std_logic; --butonul de pe placa
        sel_ssd : in std_logic_vector(1 downto 0);
        code_seg: in std_logic;
--        alu_op : out STD_LOGIC_VECTOR(4 downto 0);
--        rez : out STD_LOGIC_VECTOR(15 downto 0);
--        rez1 : out STD_LOGIC_VECTOR(15 downto 0);
        anod: out STD_LOGIC_VECTOR(3 downto 0);
        catod: out STD_LOGIC_VECTOR(6 downto 0)
   );
end Control_Unit;

architecture Behavioral of Control_Unit is
                  
signal pc_s : std_logic_vector(15 downto 0);    
signal opcode_s : std_logic_vector(4 downto 0);
signal reg1_s : std_logic_vector(2 downto 0);  
signal reg2_s : std_logic_vector(2 downto 0);  
signal imm_s : std_logic_vector(4 downto 0);   
signal mod_adr_s : std_logic_vector(1 downto 0);
signal A_s : std_logic_vector(15 downto 0);  
signal B_s : std_logic_vector(15 downto 0); 
signal result_s : STD_LOGIC_VECTOR (15 downto 0); 
signal result1_s : STD_LOGIC_VECTOR (15 downto 0);
signal branch_s : STD_LOGIC;                      
signal flags_s : STD_LOGIC_VECTOR(4 downto 0);  

signal cs_s: STD_LOGIC_VECTOR (15 downto 0);
signal ip_s: STD_LOGIC_VECTOR (15 downto 0);

signal pos_in_s: std_logic_vector(2 downto 0);  
signal write_edx: std_logic;
 
signal data1_out_s: STD_LOGIC_VECTOR (15 downto 0);
signal data2_out_s: STD_LOGIC_VECTOR (15 downto 0);

signal enable:std_logic; --semnal filtrat de la buton
signal afisare_ssd: std_logic_vector(15 downto 0);

begin


process(sel_ssd, result_s, result1_s, opcode_s)
begin
    case(sel_ssd) is
        when "00" => afisare_ssd <= result_s;
        when "01" => afisare_ssd <= result1_s;
        when others => afisare_ssd <= "00000000000" & opcode_s;
    end case;
end process;

SSD_port: entity work.SSD
    port map (
        clk => clock,
        digit1 => afisare_ssd(3 downto 0),
        digit2 => afisare_ssd(7 downto 4),
        digit3 => afisare_ssd(11 downto 8),
        digit4 => afisare_ssd(15 downto 12),
        an => anod,
        cat => catod
    );

Segment_Registers_port1: entity work.Segment_Registers
    port map (
        clk => clock,
        rst => reset,
        en => enable,
        --en => buton,
        current_code => code_seg,
        adress_bus => "000",
        segment_reg => cs_s
    );
    
Segment_Registers_port2: entity work.Segment_Registers
    port map (
        clk => clock,
        rst => reset,
        en => enable,
        --en => buton,
        current_code => code_seg,
        adress_bus => "100",
        segment_reg => ip_s
    );

Adder_port: entity work.Adder
    port map (
        segment => cs_s,
        offset => ip_s,
        result => pc_s
    );
    
Decode_Unit_port: entity work.Decode_unit
    port map (
        clock => clock,
        reset => reset,
        opcode => opcode_s,
        adr_reg1 => reg1_s,
        adr_reg2 => reg2_s,
        imm_m => imm_s,
        mod_adr => mod_adr_s,
        A => A_s,
        B => B_s
    );
    
Instruction_register_port: entity work.Instruction_register
    port map (
        clock => clock,
        pc => pc_s,
        opcode => opcode_s,
        reg1 => reg1_s,
        reg2 => reg2_s,
        imm => imm_s,
        mod_adr => mod_adr_s 
    );
        
ALU_port: entity work.ALU
    port map (
        clock => clock,
        enable => enable,
        --enable => buton,
        data_rA => A_s,
        data_rB => B_s,
        alu_op => opcode_s,
        result => result_s,
        result1 => result1_s,
        branch => branch_s,
        flags => flags_s 
    );

process(clock)
begin
    case(opcode_s) is
        when "00110" => pos_in_s <= "000"; write_edx <= '1';
        when "00111" => pos_in_s <= "000"; write_edx <= '1';
        when "10011" => pos_in_s <= "000"; write_edx <= '0';
        when others => pos_in_s <= reg1_s; write_edx <= '0';
    end case;
end process;



Registers_port1: entity work.Registers
    port map (
        pos_in => pos_in_s,
        data_in => result_s,
        write => '1',
        clock => clock,
        data_out => data1_out_s
    );
    
Registers_port2: entity work.Registers
   port map (
       pos_in => pos_in_s,
       data_in => result1_s,
       write => write_edx,
       clock => clock,
       data_out => data2_out_s
   );
   
   
MONOIMPULS: entity work.GeneratorDeMonoimpuls
    port map(
        btn => buton,
        clk => clock,
        en => enable
    );
    
--alu_op <= opcode_s;
--rez <= result_s;
--rez1 <= result1_s;

end Behavioral;
