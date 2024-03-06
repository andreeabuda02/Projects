library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity Instruction_register_sim is
--  Port ( );
end Instruction_register_sim;

architecture Behavioral of Instruction_register_sim is

signal clock_s: std_logic := '0';
signal pc_s:std_logic_vector(15 downto 0) := (others => '0'); 
signal opcode_s:std_logic_vector(4 downto 0):= (others => '0'); 
signal reg1_s:std_logic_vector(2 downto 0):= (others => '0'); 
signal reg2_s:std_logic_vector(2 downto 0):= (others => '0'); 
signal imm_s:std_logic_vector(4 downto 0):= (others => '0'); 
signal mod_adr_s:std_logic_vector(1 downto 0):= (others => '0'); 

begin
test_Instruction_register: entity WORK.Instruction_register port map(
    clock => clock_s, 
    pc => pc_s,
    opcode => opcode_s,
    reg1 => reg1_s,
    reg2 => reg2_s,
    imm => imm_s,
    mod_adr => mod_adr_s    
); 
process
  begin
    clock_s <= not clock_s;
    wait for 5 ns; 
end process;

process
begin
    pc_s <= "0000000000000001";
    wait for 10 ns;
    pc_s <= "0000000000000011";
    wait for 10 ns;
    pc_s <= "0000000000000000";
    wait for 10 ns;
    pc_s <= "0000000000000010";
    wait for 10 ns;
    pc_s <= "0000000000000100";
    wait;
end process;

end Behavioral;
