library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity Segment_Registers_sim is
--  Port ( );
end Segment_Registers_sim;

architecture Behavioral of Segment_Registers_sim is

signal clk_s : STD_LOGIC := '0';                              
signal rst_s : STD_LOGIC := '0';    
signal en_s : STD_LOGIC := '0';    
signal current_code_s : STD_LOGIC := '0';                     
signal adress_bus_s: STD_LOGIC_VECTOR(2 downto 0):= (others=> '0');
signal segment_reg_s : STD_LOGIC_VECTOR(15 downto 0):= (others=> '0');

begin

test_Segment_R: entity WORK.Segment_Registers port map(
    clk => clk_s,
    rst => rst_s,
    current_code => current_code_s, 
    adress_bus => adress_bus_s,
    segment_reg => segment_reg_s
);

process
  begin
    clk_s <= not clk_s;
    wait for 5 ns; 
end process;

process
begin
    en_s <= '0';
    rst_s <= '1';
    wait for 10 ns;
    rst_s <= '0';
    en_s <= '1';
    current_code_s <= '0';
    adress_bus_s <= "000";
    wait for 5 ns;
    current_code_s <= '1';
    wait for 10 ns;
    current_code_s <= '0';
    adress_bus_s <= "100";
    wait for 5 ns;
    current_code_s <= '1';
    
    wait;
end process;

end Behavioral;
