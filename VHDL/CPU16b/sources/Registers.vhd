library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;


-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity Registers is
Port (
    pos_in: IN std_logic_vector(2 downto 0);
    data_in: IN std_logic_vector(15 downto 0);
    write: IN std_logic;
    clock: IN std_logic;
    data_out: OUT std_logic_vector(15 downto 0)
 );
end Registers;

architecture Behavioral of Registers is

--registers(0) => AX - "000"
--registers(1) => BX
--registers(2) => CX
--registers(3) => DX
--registers(4) => SP
--registers(5) => BP
--registers(6) => DI
--registers(7) => SI

type REG_type is array (0 to 7) of STD_LOGIC_VECTOR(15 downto 0);
signal registers:REG_type:= ( 
    "0000000000000001",
    "0000000000000011",
    "0000000000000111",
    "0000000000000000",
    "0000000000000100",
others=> X"0000");

begin

process(clock)
begin
    if(rising_edge(clock)) then
        if(write = '1') then
            registers(conv_integer(pos_in)) <= data_in;
        end if;
    end if;
end process;

data_out <= registers(conv_integer(pos_in));

end Behavioral;
