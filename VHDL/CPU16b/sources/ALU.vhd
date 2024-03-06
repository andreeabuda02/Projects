library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity ALU is
    Port ( clock : in STD_LOGIC;
           enable : in STD_LOGIC;
           data_rA : in STD_LOGIC_VECTOR (15 downto 0);
           data_rB : in STD_LOGIC_VECTOR (15 downto 0);
           alu_op : in STD_LOGIC_VECTOR (4 downto 0);
           result : out STD_LOGIC_VECTOR (15 downto 0);
           result1 : out STD_LOGIC_VECTOR (15 downto 0);
           branch : out STD_LOGIC;
           flags : out STD_LOGIC_VECTOR(4 downto 0));
          
end ALU;

architecture Behavioral of ALU is

--semnale registre pt impartire
--signal cat_s : STD_LOGIC_VECTOR(15 downto 0) := (others => '0');
--signal rest_s : STD_LOGIC_VECTOR(15 downto 0) := (others => '0');
--signal deimpartit_s : STD_LOGIC_VECTOR(31 downto 0) := (others => '0');
--signal impartitor_s : STD_LOGIC_VECTOR(15 downto 0) := (others => '0');

--semnale pentru rezultat
signal result_s: STD_LOGIC_VECTOR(15 downto 0):=(others=>'0');
signal result1_s: STD_LOGIC_VECTOR(15 downto 0):=(others=>'0');


signal branch_s: STD_LOGIC:='0';
signal flags_s: STD_LOGIC_VECTOR(4 downto 0):=(others=>'0'); 
--flags(0) => CF(Carry flag)
--flags(1) => PF(Parity flag)
--flags(2) => ZF(Zero flag)
--flags(3) => SF(Sign flag)
--flags(4) => OF(Overflow flag)


signal ambele_pozitive: STD_LOGIC:='0';
signal ambele_negative: STD_LOGIC:='0';
signal semn_s: STD_LOGIC:='0';

signal mem_value_s: STD_LOGIC_VECTOR(15 downto 0) := (others => '0');

signal rez_shift_s: STD_LOGIC_VECTOR(15 downto 0) := (others => '0');


function calculare_paritate(intrare: STD_LOGIC_VECTOR) return STD_LOGIC is
    variable nr_de_unu: integer := 0;
begin
    for i in intrare'range loop
        if intrare(i) = '1' then
            nr_de_unu := nr_de_unu + 1;
        end if;
    end loop;
    
    if(nr_de_unu mod 2 = 0) then
        return '0';
    else
        return '1';
    end if;
end function;

function mul1(val1: std_logic_vector; val2: std_logic_vector) return std_logic_vector is
    variable r: std_logic_vector(31 downto 0):=(others=>'0');
begin
    r := val1 * val2;
    return r(31 downto 16);
end function;


function mul2(val1: std_logic_vector; val2: std_logic_vector) return std_logic_vector is
    variable r: std_logic_vector(31 downto 0):=(others=>'0');
begin
    r := val1 * val2;
    return r(15 downto 0);
end function;

--function div1(val1: std_logic_vector; val2: std_logic_vector) return std_logic_vector is
--    variable cat_s: std_logic_vector(15 downto 0) := (others => '0');
--    variable rest_s: std_logic_vector(15 downto 0) := (others => '0');
--    variable impartitor_s: std_logic_vector(15 downto 0);
--begin
--    impartitor_s := val2(15 downto 0);
    
--    for i in 0 to 15 loop
--        cat_s := cat_s(13 downto 0) & '0' & rest_s(15);
--        rest_s := '0' & rest_s(15 downto 1);
--        if rest_s >= impartitor_s then
--            rest_s := rest_s - impartitor_s;
--            cat_s(0) := '1';
--        end if;
--    end loop;

--    return cat_s;
--end function;

--function div2(val1: std_logic_vector; val2: std_logic_vector) return std_logic_vector is
--    variable cat_s: std_logic_vector(15 downto 0) := (others => '0');
--    variable rest_s: std_logic_vector(15 downto 0) := (others => '0');
--    variable impartitor_s: std_logic_vector(15 downto 0);
--begin
--    impartitor_s := val2(15 downto 0);
    
--    for i in 0 to 15 loop
--        cat_s := cat_s(13 downto 0) & '0' & rest_s(15);
--        rest_s := '0' & rest_s(15 downto 1);
--        if rest_s >= impartitor_s then
--            rest_s := rest_s - impartitor_s;
--            cat_s(0) := '1';
--        end if;
--    end loop;

--    return rest_s;
--end function;

--constant num_shifts : integer := to_integer(unsigned(data_rB));


begin

pozitivitate:process(clock, enable)
begin
if(rising_edge(clock)) then
    if(enable='1') then
        if(data_rA(15) = '0' and data_rB(15) = '0') then
            ambele_pozitive <= '1';
        else
            ambele_pozitive <= '0';
        end if;
        
        if(data_rA(15) = '1' and data_rB(15) = '1') then
            ambele_negative <= '1';
        else
            ambele_negative <= '0';
        end if;
    end if;
end if;
end process pozitivitate;


process(clock, enable)
begin
    if(rising_edge(clock)) then
        if(enable='1') then
            case alu_op is
          
                when "00000" =>   --add reg, reg
                    result_s <= data_rA + data_rB;  
                    flags_s(0) <= '0';
                    flags_s(1) <= calculare_paritate(result_s);
                    if(result_s = X"0000") then
                        flags_s(2) <= '1';
                    else
                        flags_s(2) <= '0';
                    end if;
                    flags_s(3) <= result_s(15);
                    if (signed(result_s) < 0 and ambele_pozitive='1') or (signed(result_s) > 0 and ambele_negative='1') then
                        flags_s(4) <= '1';
                    else
                        flags_s(4) <= '0';
                    end if;
                    branch_s <= '0';
                    
                ---------
                when "00001" =>   --add reg, mem
                    result_s <= data_rA + data_rB;
                    flags_s(0) <= '0';
                    flags_s(1) <= calculare_paritate(result_s);
                    if(result_s = X"0000") then
                        flags_s(2) <= '1';
                    else
                        flags_s(2) <= '0';
                    end if;
                    flags_s(3) <= result_s(15);
                    if (signed(result_s) < 0 and ambele_pozitive = '1') or (signed(result_s) > 0 and ambele_negative = '1') then
                        flags_s(4) <= '1';
                    else
                        flags_s(4) <= '0';
                    end if;
                    branch_s <= '0';
                    
                    
                when "00010" =>   --add reg, imm
                    result_s <= data_rA + data_rB;    
                    flags_s(0) <= '0';
                    flags_s(1) <= calculare_paritate(result_s);
                    if(result_s = X"0000") then
                        flags_s(2) <= '1';
                    else
                        flags_s(2) <= '0';
                    end if;
                    flags_s(3) <= result_s(15);
                    if (signed(result_s) < 0 and (data_rA(15) = '0' and data_rB(15) = '0')) or (signed(result_s) > 0 and (data_rA(15) = '1' and data_rB(15) = '1')) then
                        flags_s(4) <= '1';
                    else
                        flags_s(4) <= '0';
                    end if;
                    branch_s <= '0';
                    
                    
                when "00011" =>   --sub reg, reg 
                    result_s <= data_rA - data_rB; 
                    flags_s(0) <= '0';
                    flags_s(1) <= calculare_paritate(result_s);
                    if(result_s = X"0000") then
                        flags_s(2) <= '1';
                    else
                        flags_s(2) <= '0';
                    end if;
                    flags_s(3) <= result_s(15);
                    if (signed(result_s) < 0 and ambele_pozitive='1') or (signed(result_s) > 0 and ambele_negative='1') then
                        flags_s(4) <= '1';
                    else
                        flags_s(4) <= '0';
                    end if;
                    branch_s <= '0';
                    
                    
                ---------
                when "00100" =>   --sub reg, mem   
                    result_s <= data_rA - data_rB; 
                    flags_s(0) <= '0';
                    flags_s(1) <= calculare_paritate(result_s);
                    if(result_s = X"0000") then
                        flags_s(2) <= '1';
                    else
                        flags_s(2) <= '0';
                    end if;
                    flags_s(3) <= result_s(15);
                    if (signed(result_s) < 0 and ambele_pozitive = '1') or (signed(result_s) > 0 and ambele_negative = '1') then
                        flags_s(4) <= '1';
                    else
                        flags_s(4) <= '0';
                    end if;
                    branch_s <= '0';
                
                
                when "00101" =>   --sub reg, imm
                    result_s <= data_rA - data_rB;   
                    flags_s(0) <= '0';
                    flags_s(1) <= calculare_paritate(result_s);
                    if(result_s = X"0000") then
                        flags_s(2) <= '1';
                    else
                        flags_s(2) <= '0';
                    end if;
                    flags_s(3) <= result_s(15);
                    if (signed(result_s) < 0 and (data_rA(15) = '0' and data_rB(15) = '0')) or (signed(result_s) > 0 and (data_rA(15) = '1' and data_rB(15) = '1')) then
                        flags_s(4) <= '1';
                    else
                        flags_s(4) <= '0';
                    end if;
                    branch_s <= '0';
                
                -------
                when "00110" =>   --mul src_reg 
                    --mul reg16b => DX:AX <- AX * reg16b
                    result_s <= mul2(data_rA, data_rB);
                    result1_s <= mul1(data_rA, data_rB);
                   
                    flags_s(0) <= result1_s(15); 
                    flags_s(1) <= calculare_paritate(result_s);
                    
                    if(result_s = X"0000") then
                        flags_s(2) <= '1';
                    else
                        flags_s(2) <= '0';
                    end if;
                   
                    flags_s(3) <= result_s(15);
                    flags_s(4) <= '0';  
                    branch_s <= '0';
                
                -------
--                when "00111" =>   --div src_reg 
--                    --div reg16b => AX <- DX:AX / reg16b, DX <- DX:AX % reg16b
--                    result_s <= div1(data_rA, data_rB);
--                    result1_s <= div2(data_rA, data_rB);
--                    flags_s(0) <= '0';  
--                    flags_s(1) <= calculare_paritate(result_s);
--                    if result_s = "0000000000000000" and result1_s = "0000000000000000" then
--                        flags_s(2) <= '1';  
--                    else
--                        flags_s(2) <= '0';
--                    end if;
--                    flags_s(3) <= result_s(15);  
--                    flags_s(4) <= '0'; 
--                    branch_s <= '0';  
                                      
                                    
                    
                    
                when "01000" =>   --inc dest_reg
                    result_s <= data_rA + 1; 
                    flags_s(0) <= '0';
                    flags_s(1) <= calculare_paritate(result_s);
                    if(result_s = X"0000") then
                        flags_s(2) <= '1';
                    else
                        flags_s(2) <= '0';
                    end if;
                    flags_s(3) <= result_s(15);
                    if (signed(result_s) < 0 and data_rA(15) = '0') or (signed(result_s) > 0 and data_rA(15) = '1') then
                        flags_s(4) <= '1';
                    else
                        flags_s(4) <= '0';
                    end if;
                    branch_s <= '0';
                
                
                
                when "01001" =>   --dec dest_reg  
                       result_s <= data_rA - 1; 
                       flags_s(0) <= '0';
                       flags_s(1) <= calculare_paritate(result_s);
                       if(result_s = X"0000") then
                           flags_s(2) <= '1';
                       else
                           flags_s(2) <= '0';
                       end if;
                       flags_s(3) <= result_s(15);
                       if (signed(result_s) < 0 and data_rA(15) = '0') or (signed(result_s) > 0 and data_rA(15) = '1') then
                           flags_s(4) <= '1';
                       else
                           flags_s(4) <= '0';
                       end if;
                    branch_s <= '0';
                    
                
                
--                 when "01010" =>   --sal dest_reg, count
--                    result_s <= data_rA;
--                    for i in 1 to to_integer(unsigned(data_rB)) loop
--                        flags_s(0) <= result_s(15);
--                        result_s <= result_s(14 downto 0) & '0'; 
--                    end loop;
--                    result_s(15) <= flags_s(0);
--                    flags_s(1) <= calculare_paritate(result_s);
--                    if(result_s = X"0000") then
--                        flags_s(2) <= '1';
--                    else
--                        flags_s(2) <= '0';
--                    end if;
--                    flags_s(3) <= result_s(15);
--                    flags_s(4) <= '0';  --nu se calculeaza pentru shift
--                    branch_s <= '0';
                                    
                
                
--                when "01011" =>   --sar dest_reg, count  
--                    result_s <= data_rA;
--                    semn_s <= result_s(15);
--                    for i in 1 to MAX_SHIFTS loop
--                        flags_s(0) <= result_s(0);
--                        result_s <= '0' & result_s(15 downto 1); 
--                    end loop;
--                    result_s(15) <= semn_s;
--                    flags_s(1) <= calculare_paritate(result_s);
--                    if(result_s = X"0000") then
--                        flags_s(2) <= '1';
--                    else
--                        flags_s(2) <= '0';
--                    end if;
--                    flags_s(3) <= result_s(15);
--                    flags_s(4) <= '0';  --nu se calculeaza pentru shift
--                    branch_s <= '0';
                
                
                
                when "01100" =>   --and dest_reg, src_reg
                    result_s <= data_rA and data_rB;
                    flags_s(0) <= '0';
                    flags_s(1) <= calculare_paritate(result_s);
                    if(result_s = X"0000") then
                        flags_s(2) <= '1';
                    else
                        flags_s(2) <= '0';
                    end if;
                    flags_s(3) <= result_s(15);
                    flags_s(4) <= '0';  --nu se calculeaza pentru AND
                    branch_s <= '0';
                
                
                
                when "01101" =>   --or dest_reg, src_reg
                    result_s <= data_rA or data_rB;
                    flags_s(0) <= '0';
                    flags_s(1) <= calculare_paritate(result_s);
                    if(result_s = X"0000") then
                        flags_s(2) <= '1';
                    else
                        flags_s(2) <= '0';
                    end if;
                    flags_s(3) <= result_s(15);
                    flags_s(4) <= '0';  --nu se calculeaza pentru OR
                    branch_s <= '0';
                
                
                
                when "01110" =>   --not dest_reg
                    result_s <= not data_rA;
                    flags_s(0) <= '0';
                    flags_s(1) <= calculare_paritate(result_s);
                    if(result_s = X"0000") then
                        flags_s(2) <= '1';
                    else
                        flags_s(2) <= '0';
                    end if;
                    flags_s(3) <= result_s(15);
                    flags_s(4) <= '0';  --nu se calculeaza pentru NOT
                    branch_s <= '0';
                
                
                
                when "01111" =>   --xor dest_reg, src_reg
                    result_s <= data_rA xor data_rB;
                    flags_s(0) <= '0';
                    flags_s(1) <= calculare_paritate(result_s);
                    if(result_s = X"0000") then
                        flags_s(2) <= '1';
                    else
                        flags_s(2) <= '0';
                    end if;
                    flags_s(3) <= result_s(15);
                    flags_s(4) <= '0';  --nu se calculeaza pentru XOR
                    branch_s <= '0';
                
                
                ----------
                when "10000" =>   --cmp dest_reg, src_reg
                    result_s <= data_rA - data_rB;                 
                    flags_s(0) <= '0';
                    flags_s(1) <= calculare_paritate(result_s);
                    if(result_s = X"0000") then
                        flags_s(2) <= '1';
                        branch_s <= '1';
                    else
                        flags_s(2) <= '0';
                        branch_s <= '0';
                    end if;
                    flags_s(3) <= result_s(15);
                    flags_s(4) <= '0';
                    
                -------
                when "10001" =>   --mov dest_reg, src_reg
                    result_s <= data_rB;
                    flags_s(0) <= result_s(15);
                    flags_s(1) <=  calculare_paritate(result_s);
                    if(result_s = X"0000") then
                        flags_s(2) <= '1';
                    else
                        flags_s(2) <= '0';
                    end if;
                    flags_s(3) <= result_s(15);
                    flags_s(4) <= '0';
                    branch_s <= '0';
                    
                    
                -------- 
                when "10010" =>   --xchg reg1, reg2
                    result_s <= data_rB; 
                    result1_s <= data_rA;  
                    flags_s(0) <= result_s(15);
                    flags_s(1) <= calculare_paritate(result_s);
                    if (result_s = X"0000") then
                        flags_s(2) <= '1';
                    else
                        flags_s(2) <= '0';
                    end if;
                    flags_s(3) <= result_s(15);
                    flags_s(4) <= '0';
                    branch_s <= '0';  

                
                ------
--                when "10011" =>   --jz target_address 
--                    if (flags_s(2) = '1') then  -- verifica daca ZF e setat
--                        branch_s <= '1';
--                        result_s <= data_rB;  
--                    else
--                        branch_s <= '0';  
--                        result_s <= (others => '0');
--                    end if;
--                    flags_s(0) <= '0';
--                    flags_s(1) <= '0';                    
--                    flags_s(3) <= '0';
--                    flags_s(4) <= '0';
                                                  
                
                when others =>
                    result_s <= (others => '0');
                    flags_s <= (others => '0');
                    branch_s <= '0';
                
            end case;
        end if;
    end if;
end process;



result <= result_s;
result1 <= result1_s;
branch <= branch_s;
flags <= flags_s;

end Behavioral;
