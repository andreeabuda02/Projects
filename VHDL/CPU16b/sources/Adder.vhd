library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity Adder is
    Port ( 
        segment : in STD_LOGIC_VECTOR(15 downto 0);
        offset : in STD_LOGIC_VECTOR(15 downto 0);
        result : out STD_LOGIC_VECTOR(15 downto 0)
    );
end Adder;

architecture Behavioral of Adder is
begin
    result <= segment + offset; --de unde imi incepe programul cat vreau sa merg in jos
end Behavioral;