library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity RAM is
    Port (
        clk : in STD_LOGIC;
        write_en : in STD_LOGIC;
        data_in : in STD_LOGIC_VECTOR(15 downto 0);
        address_in: in STD_LOGIC_VECTOR(15 downto 0);
        data_out : out STD_LOGIC_VECTOR(15 downto 0)
    );
end RAM;

architecture Behavioral of RAM is

type ram_type is array (0 to 255) of STD_LOGIC_VECTOR(15 downto 0); 
signal RAM_s : ram_type := (
    "0000000000000010", -- Adresa 0 (=2)
    "0000000000010011", -- Adresa 1 (=19)
    "0000000000100100", -- Adresa 2 (=36)
    "0000000000111001", -- Adresa 3 (=57)
    "0000000001001010", -- Adresa 4 (=74)
    "0000000001100110", -- Adresa 5 (=102)
    "0000000010101110", -- Adresa 6 (=174)
    "0000000011110000", -- Adresa 7 (=240)
    "0000000100111010", -- Adresa 8 (=314)
    "0000000101101100", -- Adresa 9 (=332)
    "0000000110010011", -- Adresa 10 (=403)
    "0000000111001001", -- Adresa 11 (=473)
    "0000000111111111", -- Adresa 12 (=511)
    "0000001000110001", -- Adresa 13 (=481)
    "0000001001100010", -- Adresa 14 (=482)
    "0000001010010100",  -- Adresa 15 (=484)
    others => (others => '0')
);
    
signal dataO : STD_LOGIC_VECTOR(15 downto 0) := (others => '0');

begin
    process(clk)
    begin
        if rising_edge(clk) then
            if write_en = '1' then
                RAM_s(conv_integer(address_in)) <= data_in;
            else
                dataO <= RAM_s(conv_integer(address_in));
            end if;
        end if;
    end process;

    data_out <= dataO;

end Behavioral;
