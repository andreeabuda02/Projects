library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;


entity Instruction_register is
Port ( 
    clock:IN std_logic;
    pc:IN std_logic_vector(15 downto 0); 
    opcode:OUT std_logic_vector(4 downto 0);
    reg1:OUT std_logic_vector(2 downto 0);
    reg2:OUT std_logic_vector(2 downto 0); 
    imm:OUT std_logic_vector(4 downto 0);
    mod_adr:OUT std_logic_vector(1 downto 0) -- 00(directa), 01(indirecta), 10(imediata)
);
end Instruction_register;

architecture Behavioral of Instruction_register is

type rom_type is array (0 to 255) of STD_LOGIC_VECTOR(15 downto 0);


signal instructions: rom_type:= (  --lista de instructiuni
--    B"00001_011_100_00000", --cs
--    B"00011_001_001_00000",
--    B"00010_011_000_00110",
--    B"00100_010_101_00000",
--    B"00101_111_000_00001",
--    others => X"0000"

    --problema1
    -- Calcularea sumei tuturor numerelor intregi pozitive
    B"00001_001_010_00001", -- ADD R1, M[reg2] 
    B"01000_010_011_00010", -- INC R2 
    B"10000_011_100_00100", -- CMP R3, 4 
    --B"00011_100_000_01000", -- JZ exit 

    -- Calcularea sumei tuturor numerelor intregi negative
    B"00100_101_110_00011", -- SUB R5, M[reg6] 
    B"01001_110_111_00101", -- DEC R6 
    B"10000_111_000_01001", -- CMP R7, 0 
    --B"00111_000_000_01000", -- JZ exit 

    -- Inmultirea sumei obtinute in pasul 1 cu suma obtinuta in pasul 2
    B"00110_001_010_11000", -- MUL R1, R8 

    -- Verificarea daca rezultatul este zero
    B"10000_001_000_01100", -- CMP R1, 0 
    --B"01010_000_000_10000", -- JZ zero_result 
    --B"01011_000_000_10010", -- JMP exit 

    --problema2
    -- Transferarea continutului unui registru sursa intr-un registru destinatie
    B"10001_110_101_00000", -- MOV R6, R5

    -- Interschimbarea continutului a doua registre
    B"10010_011_111_00000", -- XCHG R3, R7

    -- Operatia logica "sau" intre continutul a doua registre cu stocarea rezultatului intr-un registru destinatie
    B"01101_011_100_00000", -- OR R3, R4

    -- Operatia logica "not" (negare) a continutului unui registru cu stocarea rezultatului in alt registru
    B"01110_010_000_00000", -- NOT R2

    -- Compararea continutului a doi registri si setarea flag-urilor in functie de rezultatul comparatiei
    B"10000_110_111_00000", -- CMP R6, R7

    -- Divizarea unei valori dintr-un registru la alta
    --B"00111_001_000_00000", -- DIV R1

    -- Shiftare aritmetica la stanga a continutului unui registru de un numar specificat de ori
    --B"01010_010_000_00011", -- SAL R2, 3

    -- Shiftare aritmetica la dreapta a continutului unui registru de un numar specificat de ori
    --B"01011_011_000_00010", -- SAR R3, 2

    others => X"0000"
);

signal current_instruction: STD_LOGIC_VECTOR(15 downto 0);

begin

current_instruction <= instructions(to_integer(unsigned(pc)));  --pun instructiunea la care ma aflu
opcode <= current_instruction(15 downto 11);
reg1 <= current_instruction(10 downto 8);
reg2 <= current_instruction(7 downto 5);
imm <= current_instruction(4 downto 0);

process(clock)  --decodific instructiunea in functie de opcode
begin
if(falling_edge(clock)) then
    case current_instruction(15 downto 11) is
        when "00000" => --decodificare pentru add reg, reg
            mod_adr <= "01"; 
            
        when "00001" => --decodificare pentru add reg, mem
            mod_adr <= "00"; 
            
        when "00010" => --decodificare pentru add reg, imm
            mod_adr <= "10";
            
        when "00011" => --decodificare pentru sub reg, reg  
            mod_adr <= "01";
            
        when "00100" => --decodificare pentru sub reg, mem 
            mod_adr <= "00";
            
        when "00101" => --decodificare pentru sub reg, imm  
            mod_adr <= "10";
        
        when "00110" => --decodificare pentru mul src_reg  
            mod_adr <= "01";
        
        when "00111" => --decodificare pentru div src_reg
            mod_adr <= "01";               
        
        when "01000" => --decodificare pentru inc dest_reg
            mod_adr <= "01";
        
        when "01001" => --decodificare pentru dec dest_reg
            mod_adr <= "01";
        
        when "01010" => --decodificare pentru sal dest_reg, count
            mod_adr <= "10";
            
        when "01011" => --decodificare pentru sar dest_reg, count  
            mod_adr <= "10";
                
        when "01100" => --decodificare pentru and dest_reg, src_reg
            mod_adr <= "01";
        
        when "01101" => --decodificare pentru or dest_reg, src_reg
            mod_adr <= "01";
            
        when "01110" => --decodificare pentru not dest_reg
            mod_adr <= "01";
        
        when "01111" => --decodificare pentru xor dest_reg, src_reg
            mod_adr <= "01";
        
        when "10000" => --decodificare pentru cmp dest_reg, src_reg
            mod_adr <= "01";
        
        when "10001" => --decodificare pentru mov dest_reg, src_reg
            mod_adr <= "01";
        
        when "10010" => --decodificare pentru xchg reg1, reg2
            mod_adr <= "01";
        
        when "10011" => --decodificare pentru (jz target_address) 
            mod_adr <= "10";
            
        when "10100" => --decodificare pentru shl dest_reg, count
            mod_adr <= "10";
            
        when "10101" => --decodificare pentru shr dest_reg, count
            mod_adr <= "10";
        when others =>
    end case;
end if;
end process;

end Behavioral;
