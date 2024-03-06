library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity UnitateExecutie is
    Port ( RD1 : in STD_LOGIC_VECTOR (15 downto 0);
           RD2 : in STD_LOGIC_VECTOR (15 downto 0);
           Imm : in STD_LOGIC_VECTOR (15 downto 0);
           ALUsrc : in STD_LOGIC;
           ALUop : in STD_LOGIC_VECTOR (2 downto 0);
           ALUres : out STD_LOGIC_VECTOR (15 downto 0);
           functie : in STD_LOGIC_VECTOR (2 downto 0);
           sa : in STD_LOGIC;
           zero : out STD_LOGIC);
end UnitateExecutie;

architecture Behavioral of UnitateExecutie is

signal aux: STD_LOGIC_VECTOR(15 downto 0);
signal ALUctrl: STD_LOGIC_VECTOR(2 downto 0);
signal difFlag: STD_LOGIC_VECTOR(15 downto 0);


begin

process(ALUsrc)
begin
    if(ALUsrc='0') then
        aux<=RD2;
    else
        aux<=Imm;
    end if;
end process;

difFlag<=RD1-aux;

process(difFlag)
begin
    if(difFlag=X"0000") then
        zero<='1';
    else
        zero<='0';
    end if;
end process;

StabilireOperatie:process(ALUop, functie)
begin
    case(ALUop) is
        when "000" =>
            case(functie) is
                when "000" =>ALUctrl <="000";--add
                when "001" =>ALUctrl <="001";--sub
                when "010" =>ALUctrl <="010";--sll
                when "011" =>ALUctrl <="011";--srl
                when "100" =>ALUctrl <="100";--and
                when "101" =>ALUctrl <="101";--or
                when "110" =>ALUctrl <="110";--xor
                when "111" =>ALUctrl <="111";--set less than
             end case;
         when "001" =>ALUctrl <="000";--addi
         when "010" =>ALUctrl <="000";--lw
         when "011" =>ALUctrl <="000";--sw
         when "100" =>ALUctrl <="010";--beq
         when "101" =>ALUctrl <="100";--logical and unsigned constant
         when "110" =>ALUctrl <="111";--set on less than immediat
         when "111" =>ALUctrl <="111";--j
     end case;
end process;

Calculare:process(ALUctrl, RD1, RD2)
begin
    case(ALUctrl) is
        when "000" => ALUres <= RD1 + aux;
        when "001" => ALUres <= RD1 - aux;
        when "010" => ALUres <= RD1(14 downto 0) & "0";
        when "011" => ALUres <= "0" & RD1(15 downto 1);
        when "100" => ALUres <= RD1 and aux;
        when "101" => ALUres <= RD1 or aux;
        when "110" => ALUres <= RD1 xor aux;
        when "111" => ALUres <= RD1;
                                if RD1<aux then
                                    ALUres<=aux;
                                else
                                    ALUres<=RD1;
                                end if;
    end case;

end process;

end Behavioral;
