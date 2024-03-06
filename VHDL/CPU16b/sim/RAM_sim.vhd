library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity RAM_sim is
--  Port ( );
end RAM_sim;

architecture Behavioral of RAM_sim is

signal clk_s :  STD_LOGIC := '0';
signal write_en_s :  STD_LOGIC:= '0';
signal data_in_s :  STD_LOGIC_VECTOR(15 downto 0):= (others=> '0');
signal address_in_s:  STD_LOGIC_VECTOR(15 downto 0):= (others=> '0');
signal data_out_s :  STD_LOGIC_VECTOR(15 downto 0):= (others=> '0');

begin

test_RAM: entity WORK.RAM port map (
    clk => clk_s,
    write_en => write_en_s,
    data_in => data_in_s,
    address_in => address_in_s,
    data_out => data_out_s
);

process
  begin
    clk_s <= not clk_s;
    wait for 5 ns; 
end process;
  
process
  begin
    write_en_s <= '1';
    data_in_s <= "0000000000000111";
    address_in_s <= "0000000000000001";
    wait for 10 ns;
    data_in_s <= "0000000000011111";
    address_in_s <= "0000000000000010";
    wait for 10 ns;
    data_in_s <= "0110000000011111";
    address_in_s <= "0000000000000011";
    wait for 10 ns;
    write_en_s <= '0';
    address_in_s <= "0000000000000011";
    wait for 10 ns;
    address_in_s <= "0000000000000001";
    wait for 10 ns;
    address_in_s <= "0000000000000010";
    wait for 10 ns;
    write_en_s <= '1';
    data_in_s <= "1100000000000111";
    address_in_s <= "0000000000000001";
    wait;
end process;
  
end Behavioral;
