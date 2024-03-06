----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 15.05.2023 21:43:30
-- Design Name: 
-- Module Name: GeneratorDeMonoimpuls - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- 
-- Dependencies: 
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
-- 
----------------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;


entity GeneratorDeMonoimpuls is
    Port ( btn : in STD_LOGIC;
           clk : in STD_LOGIC;
           en : out STD_LOGIC);
end GeneratorDeMonoimpuls;

architecture Behavioral of GeneratorDeMonoimpuls is

signal count: std_logic_vector(15 downto 0):=(others =>'0');
signal Q1: std_logic;
signal Q2: std_logic;
signal Q3: std_logic;

BEGIN

numarator: process(clk)
begin
    if clk='1' and clk'event then
        count <= count + 1;
    end if;
end process;

bistabilDflipflop1: process(clk)
begin
    if clk='1' and clk'event then
        if count(15 downto 0)="1111111111111111" then
            Q1 <= btn;
        end if;
    end if;
end process;

bistabilDflipflop2: process(clk)
begin
    if clk='1' and clk'event then
        Q2 <= Q1;
        Q3 <= Q2;
    end if;
end process;

en <= Q2 and (not Q3);

END Behavioral;