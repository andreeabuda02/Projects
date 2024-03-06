library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity ALU_sim is
--  Port ( );
end ALU_sim;

architecture Behavioral of ALU_sim is

signal clock_s :  STD_LOGIC := '0';                           
signal enable_s : STD_LOGIC:= '0';                      
signal data_rA_s :  STD_LOGIC_VECTOR (15 downto 0):= (others=> '0'); 
signal data_rB_s : STD_LOGIC_VECTOR (15 downto 0):= (others=> '0');    
signal alu_op_s : STD_LOGIC_VECTOR (4 downto 0):= (others=> '0');      
signal result_s : STD_LOGIC_VECTOR (15 downto 0):= (others=> '0');    
signal result1_s : STD_LOGIC_VECTOR (15 downto 0):= (others=> '0');   
signal branch_s : STD_LOGIC:= '0';                          
signal flags_s : STD_LOGIC_VECTOR(4 downto 0):= (others=> '0');      

begin

test_ALU: entity WORK.ALU port map(
    clock => clock_s,
    enable => enable_s, 
    data_rA => data_rA_s,
    data_rB => data_rB_s,
    alu_op => alu_op_s, 
    result => result_s, 
    result1 => result1_s,
    branch => branch_s, 
    flags => flags_s 
);

process
  begin
    clock_s <= not clock_s;
    wait for 5 ns; 
end process;

process
  begin
    enable_s <= '1';
    wait for 10 ns;
    -- Testeaza ADD reg, reg
    data_rA_s <= "0000000000000001";
    data_rB_s <= "0000000000000011";
    alu_op_s <= "00000";
    wait for 10 ns;
    
    -- Testeaza ADD reg, mem
    data_rA_s <= "0000000000000011";
    data_rB_s <= "0000000000000011";
    alu_op_s <= "00001";
    wait for 10 ns;
    
    -- Testeaza ADD reg, imm
    data_rA_s <= "0000000000000001";
    data_rB_s <= "0000000000000011";
    alu_op_s <= "00010";
    wait for 10 ns;
    
    -- Testeaza SUB reg, reg
    data_rA_s <= "0000000000000011";
    data_rB_s <= "0000000000000011";
    alu_op_s <= "00011";
    wait for 10 ns;
    
    -- Testeaza SUB reg, mem
    data_rA_s <= "0000000000001111";
    data_rB_s <= "0000000000000011";
    alu_op_s <= "00100";
    wait for 10 ns;
    
    -- Testeaza SUB reg, imm
    data_rA_s <= "0000000000000111";
    data_rB_s <= "0000000000000011";
    alu_op_s <= "00101";
    wait for 10 ns;
    
    -- Testeaza MUL src_reg
    data_rA_s <= "0000000001100011";
    data_rB_s <= "0000100000000010"; 
    alu_op_s <= "00110"; 
    wait for 10 ns;
    
    -- Testeaza DIV src_reg
    data_rA_s <= "0000000000000100"; 
    data_rB_s <= "0000000000000010"; 
    alu_op_s <= "00111"; 
    wait for 10 ns;
    
    -- Testeaza INC dest_reg
    data_rA_s <= "0000000000000001";
    data_rB_s <= "0000000000000000";
    alu_op_s <= "01000";
    wait for 10 ns;
    
    -- Testeaza DEC dest_reg
    data_rA_s <= "0000000000000011";
    data_rB_s <= "0000000000000000";
    alu_op_s <= "01001";
    wait for 10 ns;
    
--    -- Testeaza SAL dest_reg, count
--    data_rA_s <= "0000000000000011";
--    data_rB_s <= "0000000000000001";
--    alu_op_s <= "01010";
--    wait for 10 ns;
    
--    -- Testeaza SAR dest_reg, count
--    data_rA_s <= "0000000000000011";
--    data_rB_s <= "0000000000000001";
--    alu_op_s <= "01011";
--    wait for 10 ns;
    
    -- Testeaza AND dest_reg, src_reg
    data_rA_s <= "0000000000000011";
    data_rB_s <= "0000000000000110";
    alu_op_s <= "01100";
    wait for 10 ns;
    
    -- Testeaza OR dest_reg, src_reg
    data_rA_s <= "0000000000000011";
    data_rB_s <= "0000000000000110";
    alu_op_s <= "01101";
    wait for 10 ns;
    
    -- Testeaza NOT dest_reg
    data_rA_s <= "0000000000001111";
    data_rB_s <= "0000000000000000";
    alu_op_s <= "01110";
    wait for 10 ns;
    
    -- Testeaza XOR dest_reg, src_reg
    data_rA_s <= "0000000000000011";
    data_rB_s <= "0000000000000110";
    alu_op_s <= "01110";
    wait for 10 ns;
 
    -- Testeaza CMP dest_reg, src_reg
    data_rA_s <= "0000000000001111";
    data_rB_s <= "0000000000000001";
    alu_op_s <= "10000";
    
    -- Testeaza MOV dest_reg, src_reg
    data_rA_s <= "0000000000000000"; 
    data_rB_s <= "0000000000000011";
    alu_op_s <= "11000";
    wait for 10 ns;
    
    -- Testeaza XCHG reg1, reg2
    data_rA_s <= "0000000000000001";
    data_rB_s <= "0000000000000010";
    alu_op_s <= "11001"; 
    wait for 10 ns;
    
--    -- Testeaza JZ target_address
--    data_rA_s <= "0000000000000000"; 
--    data_rB_s <= "0000000000000000";
--    alu_op_s <= "11010"; 
--    wait for 10 ns; 
    
    
    wait;
end process;

end Behavioral;
