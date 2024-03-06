library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity ALU_testBench is
--  Port ( );
end ALU_testBench;

architecture Behavioral of ALU_testBench is

component ALU is
    Port ( clock : in STD_LOGIC;
           enable : in STD_LOGIC;
           data_rA : in STD_LOGIC_VECTOR (15 downto 0);
           data_rB : in STD_LOGIC_VECTOR (15 downto 0);
           data_imm : in STD_LOGIC_VECTOR (15 downto 0);
           alu_op : in STD_LOGIC_VECTOR (4 downto 0);
           result : out STD_LOGIC_VECTOR (15 downto 0);
           result1 : out STD_LOGIC_VECTOR (15 downto 0);
           branch : out STD_LOGIC;
           flags : out STD_LOGIC_VECTOR(4 downto 0));
          
end component;

signal clock_s :  STD_LOGIC := '0';
signal enable_s :  STD_LOGIC := '0';
signal data_rA_s :  STD_LOGIC_VECTOR (15 downto 0) := (others => '0');
signal data_rB_s :  STD_LOGIC_VECTOR (15 downto 0) := (others => '0');
signal data_imm_s :  STD_LOGIC_VECTOR (15 downto 0) := (others => '0');
signal alu_op_s :  STD_LOGIC_VECTOR (4 downto 0) := (others => '0');
signal result_s :  STD_LOGIC_VECTOR (15 downto 0) := (others => '0');
signal result1_s :  STD_LOGIC_VECTOR (15 downto 0) := (others => '0');
signal branch_s :  STD_LOGIC := '0';
signal flags_s :  STD_LOGIC_VECTOR(4 downto 0) := (others => '0');

constant T : time := 20 ns;

begin

ALU_test: ALU
port map (
      clock => clock_s,
      enable => enable_s,
      data_rA => data_rA_s,
      data_rB => data_rB_s,
      data_imm => data_imm_s,
      alu_op => alu_op_s,
      result => result_s,
      result1 => result1_s,
      branch => branch_s,
      flags => flags_s
);

clock_process: process
  begin
    wait for T / 2;
    clock_s <= not clock_s;
end process clock_process;

test_process:process
begin
    wait for 20 ns; 
    enable_s <= '1';
    wait for 20 ns; 
    -- Test 1: Adunare ontre doi registri
    data_rA_s <= "0000000000000010"; -- 2
    data_rB_s <= "0000000000000011"; -- 3
    alu_op_s <= "00000"; -- Adunare
    wait for 10 ns;
    
    
    wait;
end process test_process;

end Behavioral;
