----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 15.05.2023 21:44:45
-- Design Name: 
-- Module Name: InstructionFetch - Behavioral
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

entity InstructionFetch is
    Port ( clk : in STD_LOGIC;
           reset : in STD_LOGIC;
           en : in STD_LOGIC;
           AdrBranch : in STD_LOGIC_VECTOR (15 downto 0);
           AdrJump : in STD_LOGIC_VECTOR (15 downto 0);
           Jump : in STD_LOGIC;
           PCsrc : in STD_LOGIC;
           instructiune : out STD_LOGIC_VECTOR (15 downto 0);
           urmInstructiune : out STD_LOGIC_VECTOR (15 downto 0));
end InstructionFetch;

architecture Behavioral of InstructionFetch is

type rom_type is array (0 to 255) of STD_LOGIC_VECTOR(15 downto 0);

signal ROM: rom_type:= (
                        --zona declarativa
                        B"001_000_001_0000000", --ADDI $1,$0,0  (int max=0) #2080
                        B"001_000_010_0000000", --ADDI $2,$0,0  (int suma=0) #2100
                        B"001_000_011_0000001", --ADDI $3,$0,1  (int i=1) #2181
                        B"001_000_100_0000101", --ADDI $4,$0,5  (int n=5) #2205
                        B"010_000_101_0000000", --LW $5,0($0) (v[0])(memorie[0]) #4280
                        B"010_000_110_0000001", --LW $6,1($0) (v[1])(memorie[1]) #4301
                        B"000_000_000_000_0_000", --NoOp
                        B"000_000_000_000_0_000", --NoOp
                        B"000_101_110_001_0_000", --ADD $1,$5,$6  (max=v[0]+v[1]) #1710
                        --bucla for                    
                        B"010_011_101_0000000", --LW $5,0($3) (v[i]) #4E80
                        B"010_011_110_0000001", --LW $6,1($3) (v[i+1]) #4F01
                        B"000_000_000_000_0_000", --NoOp
                        B"000_000_000_000_0_000", --NoOp
                        B"000_101_110_010_0_000", --ADD $2,$5,$6  (suma=v[i]+v[i+1]) #1720
                        B"000_000_000_000_0_000", --NoOp
                        B"000_000_000_000_0_000", --NoOp
                        B"000_010_001_001_0_111", --SLT $1,$2,$1 (if(suma>max)then max=suma) #0897
                        B"001_011_011_0000001", --ADDI $3,$3,1  (int i=i+1) #2D81
                        B"000_000_000_000_0_000", --NoOp
                        B"000_000_000_000_0_000", --NoOp
                        B"100_011_100_0000101", --BEQ $3,$4,5 (sare peste adresa de jump daca i=n) #8E05
                        B"000_000_000_000_0_000", --NoOp
                        B"000_000_000_000_0_000", --NoOp
                        B"000_000_000_000_0_000", --NoOp
                        B"111_0000000001001", --JMP 9 #E009
                        B"000_000_000_000_0_000", --NoOp
                        --
                        X"00DA",
                        
                        others => X"0000");

signal adr: STD_LOGIC_VECTOR (15 downto 0):= (others => '0');
signal adrUrm: STD_LOGIC_VECTOR (15 downto 0):= (others => '0');
signal q: STD_LOGIC_VECTOR (15 downto 0);
signal qUrm: STD_LOGIC_VECTOR (15 downto 0):= (others => '0');

begin

PC: process(clk)
begin
    if(rising_edge(clk)) then
        if en='1' then
            q<=adrUrm;
        end if;
    end if;
    if reset = '1' then
        q <= X"0000";
    end if;
end process;

instructiune<=ROM(conv_integer(q));

qUrm<=q+1;
urmInstructiune<=qUrm;

ADRESA: process(clk)
begin
   if PCsrc = '1' then
        adr<=AdrBranch;
   else 
        adr<=qUrm;
   end if; 
end process;

ADRESAURMATOARE: process(clk)
begin
    if Jump='1' then
        adrUrm<=AdrJump;
    else
        adrUrm<=adr;
    end if;
end process; 

end Behavioral;
