library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity Adder_sim is
--  Port ( );
end Adder_sim;

architecture Behavioral of Adder_sim is

signal segment_s : STD_LOGIC_VECTOR(15 downto 0):= (others=> '0');
signal offset_s : STD_LOGIC_VECTOR(15 downto 0):= (others=> '0');
signal result_s : STD_LOGIC_VECTOR(15 downto 0):= (others=> '0');

begin

test_Adder: entity WORK.Adder port map (
    segment => segment_s,
    offset => offset_s,
    result => result_s
);
  
process
  begin
    segment_s <= "0000000000000011";
    offset_s <= "0000000000000101";
    wait for 10 ns;
    segment_s <= "0000000000000010";
    offset_s <= "0000000000000111";
    wait for 10 ns;
    segment_s <= "0000000000000011";
    offset_s <= "0000000000000001";
    wait for 10 ns;
    segment_s <= "0000000000000000";
    offset_s <= "0000000000100101";
    wait;
end process;

end Behavioral;
