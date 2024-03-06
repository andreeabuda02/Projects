library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity Decode_Unit_sim is
--  Port ( );
end Decode_Unit_sim;

architecture Behavioral of Decode_Unit_sim is

signal clock_s: std_logic := '0';
signal reset_s: std_logic := '0';
signal opcode_s: std_logic_vector(4 downto 0):=(others => '0');   
signal adr_reg1_s: std_logic_vector(2 downto 0):=(others => '0');   
signal adr_reg2_s: std_logic_vector(2 downto 0):=(others => '0');    
signal imm_m_s: std_logic_vector(4 downto 0):=(others => '0');       
signal mod_adr_s: std_logic_vector(1 downto 0):=(others => '0');     
signal A_s: std_logic_vector(15 downto 0):=(others => '0');         
signal B_s: std_logic_vector(15 downto 0):=(others => '0');      
signal pc_s: std_logic_vector(15 downto 0):=(others => '0');      

begin

test_Decode_Unit: entity WORK.Decode_Unit port map(
    clock => clock_s,
    reset => reset_s,
    opcode => opcode_s,
    adr_reg1 => adr_reg1_s,
    adr_reg2 => adr_reg2_s,
    imm_m => imm_m_s,
    mod_adr => mod_adr_s,
    A => A_s,
    B => B_s
);

mod_adresare: entity WORK.Instruction_register port map(
    clock => clock_s,      
    pc => pc_s,
    opcode => opcode_s,
    reg1 => adr_reg1_s,
    reg2 => adr_reg2_s, 
    imm => imm_m_s,   
    mod_adr => mod_adr_s
);

process
  begin
    clock_s <= not clock_s;
    wait for 5 ns; 
end process;

process
begin
    reset_s <= '1';
    wait for 5 ns;
    reset_s <= '0';
    wait for 5 ns;
    pc_s <= "0000000000000000";
    wait for 10 ns;
    pc_s <= "0000000000000010";
    wait for 10 ns;
    pc_s <= "0000000000000001";
    wait;
end process;


end Behavioral;
