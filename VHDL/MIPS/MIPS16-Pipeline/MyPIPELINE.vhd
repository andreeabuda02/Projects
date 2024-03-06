----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 16.05.2023 22:43:50
-- Design Name: 
-- Module Name: MyPIPELINE - Behavioral
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


entity MyPIPELINE is
    Port ( clk : in STD_LOGIC;
           anod : out STD_LOGIC_VECTOR (3 downto 0);
           catod : out STD_LOGIC_VECTOR (6 downto 0);
           switch : in STD_LOGIC_VECTOR (15 downto 0);
           led : out STD_LOGIC_VECTOR (15 downto 0);
           buton : in STD_LOGIC_VECTOR (4 downto 0));
end MyPIPELINE;

architecture Behavioral of MyPIPELINE is

component InstructionFetch is
    Port ( clk : in STD_LOGIC;
           reset : in STD_LOGIC;
           en : in STD_LOGIC;
           AdrBranch : in STD_LOGIC_VECTOR (15 downto 0);
           AdrJump : in STD_LOGIC_VECTOR (15 downto 0);
           Jump : in STD_LOGIC;
           PCsrc : in STD_LOGIC;
           instructiune : out STD_LOGIC_VECTOR (15 downto 0);
           urmInstructiune : out STD_LOGIC_VECTOR (15 downto 0));
end component InstructionFetch;

component SSD is
    Port ( clk : in STD_LOGIC;
           digit1 : in STD_LOGIC_VECTOR(3 downto 0);
           digit2 : in STD_LOGIC_VECTOR(3 downto 0);
           digit3 : in STD_LOGIC_VECTOR(3 downto 0);
           digit4 : in STD_LOGIC_VECTOR(3 downto 0);
           an: out STD_LOGIC_VECTOR(3 downto 0);
           cat: out STD_LOGIC_VECTOR(6 downto 0));
end component SSD;

component GeneratorDeMonoimpuls is
    Port ( btn : in STD_LOGIC;
           clk : in STD_LOGIC;
           en : out STD_LOGIC);
end component GeneratorDeMonoimpuls;

component UnitateExecutie is
    Port ( RD1 : in STD_LOGIC_VECTOR (15 downto 0);
           RD2 : in STD_LOGIC_VECTOR (15 downto 0);
           Imm : in STD_LOGIC_VECTOR (15 downto 0);
           ALUsrc : in STD_LOGIC;
           ALUop : in STD_LOGIC_VECTOR (2 downto 0);
           ALUres : out STD_LOGIC_VECTOR (15 downto 0);
           functie : in STD_LOGIC_VECTOR (2 downto 0);
           sa : in STD_LOGIC;
           zero : out STD_LOGIC);
end component UnitateExecutie;

component InstructionDecoder is
    Port ( clk : in STD_LOGIC;
           enableD : in STD_LOGIC;
           RegWrite : in STD_LOGIC;
           instructiune : in STD_LOGIC_VECTOR (15 downto 0);
           WA : in STD_LOGIC_VECTOR(2 downto 0);
           ExtOp : in STD_LOGIC;
           WD : in STD_LOGIC_VECTOR (15 downto 0);
           RD1 : out STD_LOGIC_VECTOR (15 downto 0);
           RD2 : out STD_LOGIC_VECTOR (15 downto 0);
           ExtImm : out STD_LOGIC_VECTOR (15 downto 0);
           functie : out STD_LOGIC_VECTOR (2 downto 0);
           sa : out STD_LOGIC);
end component InstructionDecoder;

component RAM is
    Port ( clk:in STD_LOGIC;
           en: in STD_LOGIC;
           INTRARE :in STD_LOGIC_VECTOR (15 downto 0);
           RD:in STD_LOGIC_VECTOR (15 downto 0);
           MemW : in STD_LOGIC;
           MemD : out STD_LOGIC_VECTOR (15 downto 0);
           IESIRE : out STD_LOGIC_VECTOR (15 downto 0));
end component RAM;

--Semnale pentru UnitateExecutie

signal zero: STD_LOGIC := '0';
signal ALUres: STD_LOGIC_VECTOR(15 downto 0) := (others => '0');

--Semnale pentru RAM

signal DataIn: STD_LOGIC_VECTOR(15 downto 0) := (others => '0');
signal DataOut: STD_LOGIC_VECTOR(15 downto 0) := (others => '0');

--Semnale pentru InstructionFetch

signal enFetch: STD_LOGIC;
signal resetFetch: STD_LOGIC;
signal AdrJump: STD_LOGIC_VECTOR(15 downto 0) := (others => '0');
signal AdrBranch: STD_LOGIC_VECTOR(15 downto 0) := (others => '0');
signal instrFetch: STD_LOGIC_VECTOR(15 downto 0) := (others => '0');
signal urmInstr: STD_LOGIC_VECTOR(15 downto 0) := (others => '0');
signal RezSSD: STD_LOGIC_VECTOR(15 downto 0) := (others => '0');

--Semnale pentru InstructionDecoder

signal RegDestinatie: std_logic;
signal ExtOp: std_logic;
signal ALUsrc: std_logic;
signal ALUop: std_logic_vector(2 downto 0);
signal MemWrite: std_logic;
signal MemToReg: std_logic;
signal RegWrite: std_logic;
signal BranchD: std_logic;
signal JumpD: std_logic;
signal functieID: STD_LOGIC_VECTOR(2 downto 0) := (others => '0');
signal RD1: STD_LOGIC_VECTOR(15 downto 0) := (others => '0');
signal RD2: STD_LOGIC_VECTOR(15 downto 0) := (others => '0');
signal Imm: STD_LOGIC_VECTOR(15 downto 0) := (others => '0');
signal sa: STD_LOGIC;

--Semnale pentru iesiri

signal WD: STD_LOGIC_VECTOR(15 downto 0) := (others => '0');
signal PCsrc: STD_LOGIC := '0';

--------REGISTRII PIPELINE
--IF_ID
signal PCinc_IF_ID: STD_LOGIC_VECTOR(15 downto 0);
signal Instruction_IF_ID: STD_LOGIC_VECTOR(15 downto 0);

--ID_EX
signal PCinc_ID_EX: STD_LOGIC_VECTOR(15 downto 0);
signal RD1_ID_EX: STD_LOGIC_VECTOR(15 downto 0);
signal RD2_ID_EX: STD_LOGIC_VECTOR(15 downto 0);
signal Ext_imm_ID_EX: STD_LOGIC_VECTOR(15 downto 0);
signal func_ID_EX: STD_LOGIC_VECTOR(2 downto 0);
signal rt_ID_EX: STD_LOGIC_VECTOR(2 downto 0);
signal rd_ID_EX: STD_LOGIC_VECTOR(2 downto 0);
signal ALUOp_ID_EX: STD_LOGIC_VECTOR(2 downto 0);
signal sa_ID_EX: STD_LOGIC;
signal MemtoReg_ID_EX: STD_LOGIC;
signal RegWrite_ID_EX: STD_LOGIC;
signal MemWrite_ID_EX: STD_LOGIC;
signal Branch_ID_EX: STD_LOGIC;
signal ALUSrc_ID_EX: STD_LOGIC;
signal RegDst_ID_EX: STD_LOGIC;

--EX_MEM
signal BranchAddress_EX_MEM: STD_LOGIC_VECTOR(15 downto 0);
signal ALURes_EX_MEM: STD_LOGIC_VECTOR(15 downto 0);
signal RD2_EX_MEM: STD_LOGIC_VECTOR(15 downto 0);
signal rd_EX_MEM: STD_LOGIC_VECTOR(2 downto 0);
signal zero_EX_MEM: STD_LOGIC;
signal MemtoReg_EX_MEM: STD_LOGIC;
signal RegWrite_EX_MEM: STD_LOGIC;
signal MemWrite_EX_MEM: STD_LOGIC;
signal Branch_EX_MEM: STD_LOGIC;

--MEM_WB
signal MemData_MEM_WB: STD_LOGIC_VECTOR(15 downto 0);
signal ALUREs_MEM_WB: STD_LOGIC_VECTOR(15 downto 0);
signal rd_MEM_WB: STD_LOGIC_VECTOR(2 downto 0);
signal MemtoReg_MEM_WB: STD_LOGIC;
signal RegWrite_MEM_WB: STD_LOGIC;

BEGIN

--butoane: reset, enable

monopulse1:GeneratorDeMonoimpuls port map(buton(0),clk, enFetch);
monopulse2:GeneratorDeMonoimpuls port map(buton(1),clk, resetFetch);

-- main units

InstructionFetchPort: InstructionFetch port map(clk,resetFetch,enFetch,BranchAddress_EX_MEM,AdrJump,JumpD,PCsrc,instrFetch,urmInstr);
InstructionDecoderPort: InstructionDecoder port map(clk,enFetch,RegWrite_MEM_WB,Instruction_IF_ID,rd_MEM_WB,ExtOp,WD,RD1,RD2,Imm,functieID,sa);
CalculareALUPort: UnitateExecutie port map(RD1_ID_EX,RD2_ID_EX,Ext_imm_ID_EX,ALUsrc_ID_EX,ALUop_ID_EX,ALUres,func_ID_EX,sa_ID_EX,zero);
MemorieRAMPort: RAM port map(clk,enFetch,ALURes_EX_MEM,RD2_EX_MEM,MemWrite_EX_MEM,DataIn,DataOut);
SSDPortDigit1:SSD port map(clk, RezSSD(3 downto 0),RezSSD(7 downto 4),RezSSD(11 downto 8),RezSSD(15 downto 12),anod,catod);


afisare:process
begin
    case switch(7 downto 5) is
        when "000" => RezSSD <=RD1;
        when "001" => RezSSD <=RD2;
        when "010" => RezSSD <=Imm;
        when "011" => RezSSD <=instrFetch;
        when "100" => RezSSD <=urmInstr;
        when "101" => RezSSD <=ALUres;
        when "110" => RezSSD <=DataIn;
        when "111" => RezSSD <=WD;
    end case;
end process;

process(MemtoReg_MEM_WB,MemData_MEM_WB,ALURes_MEM_WB)
begin
    case(MemtoReg_MEM_WB) is
        when '1' => WD<=MemData_MEM_WB;
        when '0' => WD<=ALURes_MEM_WB;
    end case;
end process;

PCsrc<=Branch_EX_MEM and zero_EX_MEM;

AdrJump <= PCinc_IF_ID(15 downto 13) & Instruction_IF_ID(12 downto 0);
AdrBranch <= Ext_Imm_ID_EX + PCinc_ID_EX;

atribuireINSTR:process(Instruction_IF_ID(15 downto 0))
begin

RegDestinatie <= '0'; ExtOp <= '0'; ALUSrc <= '0'; BranchD <= '0'; JumpD <= '0'; MemWrite <= '0' ; MemToReg <= '0'; RegWrite <= '0';

case Instruction_IF_ID(15 downto 13) is
    when "000"=>--instructiune R
        RegDestinatie <= '1'; 
        RegWrite <= '1'; 
        ALUop <= "000";
    when "001"=>--addi
        RegWrite <= '1';
        ALUsrc <= '1';
        ExtOp <= '1';
        ALUop <= "001";    
    when "010"=> --load word
        RegWrite <= '1';
        ALUsrc <= '1';
        ExtOp <= '1';
        MemToReg <= '1';
        ALUop<="010";
    when "011"=>--store word
        ALUsrc <= '1';   
        ExtOp <= '1';     
        MemWrite <= '1'; 
        ALUop <= "011";          
    when "100"=>--beq
        ExtOp <= '1';
        BranchD <= '1'; 
        ALUop <= "100";   
    when "101"=>--logical and unsigned constant
        RegWrite <= '1';
        ALUsrc <= '1';
        ALUop <= "101";   
    when "110"=>--set on less than immediat
        RegWrite <= '1';
        ALUsrc <= '1';
        ALUop<="110";
    when "111"=>--jump
        ALUop <= "111";  
        JumpD <= '1'; 
end case;

end process;

led(10 downto 0) <= ALUop & regDestinatie & ExtOp & ALUsrc & BranchD & JumpD & MemWrite & MemToReg & RegWrite;
led(15) <= PCsrc;


--proces IF/ID

process(clk)
begin
    if(rising_edge(clk)) then
        if(enFetch='1') then
            Instruction_IF_ID <= instrFetch;
            PCinc_IF_ID <= urmInstr;
        end if;
    end if;
end process;


--process ID/EX

process(clk)
begin
    if(rising_edge(clk)) then
        if(enFetch='1') then
             PCinc_ID_EX <= PCinc_IF_ID;
             RD1_ID_EX <= RD1;
             RD2_ID_EX <= RD2;
             Ext_imm_ID_EX <= Imm;
             func_ID_EX <= Instruction_IF_ID(2 downto 0);
             rt_ID_EX <= Instruction_IF_ID(6 downto 4);
             rd_ID_EX <= Instruction_IF_ID(9 downto 7);
             ALUOp_ID_EX <= ALUop;
             sa_ID_EX <= Instruction_IF_ID(3);
             MemtoReg_ID_EX <= MemToReg; 
             RegWrite_ID_EX <= RegWrite;
             MemWrite_ID_EX <= MemWrite;
             Branch_ID_EX <= BranchD;
             ALUSrc_ID_EX <= ALUsrc;
             RegDst_ID_EX <= RegDestinatie;
        end if;
    end if;
end process;

--proces EX/MEM

process(clk)
begin
    if(rising_edge(clk)) then
        if(enFetch='1') then
            BranchAddress_EX_MEM <= AdrBranch;
            ALURes_EX_MEM <= ALUres;
            RD2_EX_MEM <= RD2_ID_EX;
            if(RegDst_ID_EX='1') then
                rd_EX_MEM <= rt_ID_EX;
            else
                rd_EX_MEM <= rd_ID_EX;
            end if;
            zero_EX_MEM <= zero;
            MemtoReg_EX_MEM <= MemtoReg; 
            RegWrite_EX_MEM <= RegWrite;
            MemWrite_EX_MEM <= MemWrite;
            Branch_EX_MEM <= BranchD;
        end if;
    end if;
end process;

--proces MEM/WB

process(clk)
begin
    if(rising_edge(clk)) then
        if(enFetch='1') then
            MemData_MEM_WB <= DataOut;
            ALUREs_MEM_WB <= DataIn;
            rd_MEM_WB <= rd_EX_MEM;
            MemtoReg_MEM_WB <= MemtoReg; 
            RegWrite_MEM_WB <= RegWrite;
        end if;
    end if;
end process;

END Behavioral;
