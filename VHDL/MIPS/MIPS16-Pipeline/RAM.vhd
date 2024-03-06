----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 15.05.2023 21:47:36
-- Design Name: 
-- Module Name: RAM - Behavioral
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

entity RAM is
    Port ( clk:in STD_LOGIC;
           en: in STD_LOGIC;
           INTRARE :in STD_LOGIC_VECTOR (15 downto 0);
           RD:in STD_LOGIC_VECTOR (15 downto 0);
           MemW : in STD_LOGIC;
           MemD : out STD_LOGIC_VECTOR (15 downto 0);
           IESIRE : out STD_LOGIC_VECTOR (15 downto 0));
end RAM;

architecture Behavioral of RAM is

type ram_type is array (0 to 15) of STD_LOGIC_VECTOR (15 downto 0);


signal RAM: ram_type := (
    X"0002",X"0003",X"0004",X"0005",X"0002",X"0004",
    others => X"0000");
begin

process(clk)
begin
    if rising_edge(clk) then
        if en = '1' and MemW = '1' then
            RAM(conv_integer(INTRARE(7 downto 0)))<=RD;
        end if;
    end if;
end process;

MemD<=RAM(conv_integer(INTRARE(7 downto 0)));
IESIRE<=INTRARE;
end Behavioral;
