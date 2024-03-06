library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity Decode_Unit is
  Port ( 
    clock: IN std_logic;
    reset: IN std_logic;
    opcode:IN std_logic_vector(4 downto 0);
    adr_reg1:IN std_logic_vector(2 downto 0);
    adr_reg2:IN std_logic_vector(2 downto 0); 
    imm_m:IN std_logic_vector(4 downto 0);
    mod_adr:IN std_logic_vector(1 downto 0);
    A:OUT std_logic_vector(15 downto 0);
    B:OUT std_logic_vector(15 downto 0)
  );
end Decode_Unit;

architecture Behavioral of Decode_Unit is

signal val_imm_s: std_logic_vector(15 downto 0);
signal mem_s: std_logic_vector(15 downto 0); --val din memorie
signal rA, rB: std_logic_vector(15 downto 0);
signal mem_seg_s: std_logic_vector(15 downto 0); --de unde incepe segmentul memoriei
signal pos_mem: std_logic_vector(15 downto 0); --(imm_m)-offset de la ds pana la val pe care vreau sa o extrag din memorie
signal adresa_memorie_rez: std_logic_vector(15 downto 0);


begin

pos_mem <= "00000000000" & imm_m;

regs1: entity WORK.Registers port map (
    pos_in => adr_reg1,
    data_in => "0000000000000000",
    write => '0',
    clock => clock,
    data_out => rA
);

regs2: entity WORK.Registers port map (
    pos_in => adr_reg2,
    data_in => "0000000000000000",
    write => '0',
    clock => clock,
    data_out => rB
);

segments: entity WORK.Segment_Registers port map(
    clk => clock,
    rst => reset,
    en => '1',
    current_code => '0',
    adress_bus => "001", --ds
    segment_reg => mem_seg_s
);

adder_p1: entity WORK.Adder port map (
    segment => mem_seg_s,
    offset => pos_mem,
    result => adresa_memorie_rez
);

memorie: entity WORK.RAM port map(
    clk => clock,
    write_en => '0',
    data_in => "0000000000000000",
    address_in => adresa_memorie_rez,
    data_out => mem_s
    
);

val_imm_s <= "00000000000" & imm_m when imm_m(4)='0' else "11111111111" & imm_m;

A <= rA;
B <= rB when mod_adr = "01" else mem_s when mod_adr = "00" else val_imm_s;

end Behavioral;
