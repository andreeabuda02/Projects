library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity Control_Unit_sim is
--  Port ( );
end Control_Unit_sim;

architecture Behavioral of Control_Unit_sim is

signal clock_s : STD_LOGIC := '0';                   
signal reset_s : STD_LOGIC:= '0';    
signal buton_s : std_logic:= '0';
signal sel_ssd_s : std_logic_vector(1 downto 0):=(others => '0');               
signal code_seg_s: std_logic:= '0';                       
signal alu_op_s : STD_LOGIC_VECTOR(4 downto 0):=(others => '0');
signal rez_s : STD_LOGIC_VECTOR(15 downto 0):=(others => '0');
signal rez1_s : STD_LOGIC_VECTOR(15 downto 0):=(others => '0');
signal anod_s: STD_LOGIC_VECTOR(3 downto 0):=(others => '0');  
signal catod_s: STD_LOGIC_VECTOR(6 downto 0):=(others => '0');     

begin

test_Control_Unit: entity WORK.Control_Unit port map(
    clock => clock_s,
    reset => reset_s,
    buton => buton_s,
    sel_ssd => sel_ssd_s,
    code_seg => code_seg_s,
    --alu_op => alu_op_s,
    --rez => rez_s,
    --rez1 => rez1_s,
    anod => anod_s,
    catod => catod_s
);


process
  begin
    clock_s <= not clock_s;
    wait for 5 ns; 
end process;

process
begin
    buton_s <= '0';
    reset_s <= '1';
    wait for 10 ns;
    reset_s <= '0';
    buton_s <= '1';
    wait;
end process;

end Behavioral;
