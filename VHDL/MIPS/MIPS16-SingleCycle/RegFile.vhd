
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity RegFile is
  Port (clk: in STD_LOGIC;
        en: in STD_LOGIC;
        RA1: in STD_LOGIC_VECTOR(2 downto 0);
        RA2: in STD_LOGIC_VECTOR(2 downto 0);
        WA: in STD_LOGIC_VECTOR(2 downto 0);
        WD: in STD_LOGIC_VECTOR(15 downto 0);
        WE: in STD_LOGIC;
        rd1: out STD_LOGIC_VECTOR(15 downto 0);
        rd2: out STD_LOGIC_VECTOR(15 downto 0));
end RegFile;

architecture Behavioral of RegFile is

type reg_array is array(0 to 15) of STD_LOGIC_VECTOR(15 downto 0);
signal reg_file : reg_array := (others => X"0000");

begin

--write port
    write: process(clk)
    begin
        if rising_edge(clk) then
            if WE = '1' and en = '1' then
                reg_file(conv_integer(WA)) <= WD;
            end if;
        end if;
    end process;
    
    rd1<=reg_file(conv_integer(RA1));
    rd2<=reg_file(conv_integer(RA2));
end Behavioral;
