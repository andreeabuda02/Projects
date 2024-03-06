library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity Segment_Registers is
  Port (
    clk : in STD_LOGIC;
    rst : in STD_LOGIC;
    en : in std_logic;
    current_code : in STD_LOGIC;
    adress_bus: in STD_LOGIC_VECTOR(2 downto 0);
    segment_reg : out STD_LOGIC_VECTOR(15 downto 0)
  );
end Segment_Registers;

architecture Behavioral of Segment_Registers is

signal ip_reg_s: STD_LOGIC_VECTOR(15 downto 0):=(others => '0');
signal seg_reg_cs_s: STD_LOGIC_VECTOR(15 downto 0):=(others => '0');
signal seg_reg_ds_s: STD_LOGIC_VECTOR(15 downto 0):=(others => '0');
signal seg_reg_ss_s: STD_LOGIC_VECTOR(15 downto 0):=(others => '0');
signal seg_reg_es_s: STD_LOGIC_VECTOR(15 downto 0):=(others => '0');

signal segment_reg_s: STD_LOGIC_VECTOR(15 downto 0):=(others => '0');


begin
  process (clk, rst, en)
  begin
    
    if rst = '1' then
      ip_reg_s <= (others => '0');
    elsif falling_edge(clk) then  
        if(en = '1') then  
          case adress_bus is
            when "000" => segment_reg_s <= seg_reg_cs_s;
            when "001" => segment_reg_s <= seg_reg_ds_s;
            when "010" => segment_reg_s <= seg_reg_ss_s;
            when "011" => segment_reg_s <= seg_reg_es_s;
            when others => ip_reg_s <= ip_reg_s + 1; 
                           segment_reg_s <= ip_reg_s;
          end case;
        end if;
    end if;
    
    if rising_edge(clk) then   
        if(en = '1') then
            if(current_code = '0') then
              seg_reg_cs_s <= "0000000000000000";
            else
              seg_reg_cs_s <= "0000000000001000";
            end if;
        end if;
         
    end if;
    
  end process;

segment_reg <= segment_reg_s;

end Behavioral;
