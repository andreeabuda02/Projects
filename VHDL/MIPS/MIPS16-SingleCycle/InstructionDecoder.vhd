
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;


entity InstructionDecoder is
    Port ( clk : in STD_LOGIC;
           enableD : in STD_LOGIC;
           RegWrite : in STD_LOGIC;
           instructiune : in STD_LOGIC_VECTOR (15 downto 0);
           RegDst : in STD_LOGIC;
           ExtOp : in STD_LOGIC;
           WD : in STD_LOGIC_VECTOR (15 downto 0);
           RD1 : out STD_LOGIC_VECTOR (15 downto 0);
           RD2 : out STD_LOGIC_VECTOR (15 downto 0);
           ExtImm : out STD_LOGIC_VECTOR (15 downto 0);
           functie : out STD_LOGIC_VECTOR (2 downto 0);
           sa : out STD_LOGIC);
end InstructionDecoder;

architecture Behavioral of InstructionDecoder is

component RegFile is
  Port (clk: in STD_LOGIC;
        en: in STD_LOGIC;
        RA1: in STD_LOGIC_VECTOR(2 downto 0);
        RA2: in STD_LOGIC_VECTOR(2 downto 0);
        WA: in STD_LOGIC_VECTOR(2 downto 0);
        WD: in STD_LOGIC_VECTOR(15 downto 0);
        WE: in STD_LOGIC;
        rd1: out STD_LOGIC_VECTOR(15 downto 0);
        rd2: out STD_LOGIC_VECTOR(15 downto 0));
end component RegFile;

signal adrScrisa: STD_LOGIC_VECTOR(2 downto 0);

begin

process(clk)
begin
    if RegDst='0' then
        adrScrisa<=instructiune(9 downto 7);
    else
        adrScrisa<=instructiune(6 downto 4);
    end if;
end process;

process(clk)
begin
    if ExtOp='0' then
        ExtImm<="000000000" & instructiune(6 downto 0);
    else
        if instructiune(6)='0' then
            ExtImm<="000000000" & instructiune(6 downto 0);
        else
            ExtImm<="111111111" & instructiune(6 downto 0);
        end if;
    end if;
end process;

functie<=instructiune(2 downto 0);
sa<=instructiune(3);

RegFile8biti: RegFile port map (clk,enableD,instructiune(12 downto 10), instructiune(9 downto 7),adrScrisa,WD,RegWrite,RD1,RD2);

end Behavioral;
