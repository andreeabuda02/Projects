.386
.model flat, stdcall
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;includem biblioteci, si declaram ce functii vrem sa importam
includelib msvcrt.lib
extern exit: proc
extern malloc: proc
extern memset: proc

includelib canvas.lib
extern BeginDrawing: proc
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;declaram simbolul start ca public - de acolo incepe executia
public start
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;sectiunile programului, date, respectiv cod
.data
;aici declaram date
vector DD 100 DUP (0)

ratari DD 0
succes DD 0
parti DD 23

window_title DB "Exemplu proiect desenare",0
area_width EQU 1500
area_height EQU 700
area DD 0
len DD 0

counter DD 0 ; numara evenimentele de tip timer
counterOK DD 0 ;numara cat timp a trecut de cand s-a afisat textul ok
counterHarta DD 0

arg1 EQU 8
arg2 EQU 12
arg3 EQU 16
arg4 EQU 20

symbol_width EQU 10
symbol_height EQU 20
color_width EQU 48
color_height EQU 39

x DD 0
y DD 0
i DD 0
j DD 0

include digits.inc
include letters.inc
include Table.inc
include semne.inc
include colorare.inc

button_hx EQU 100
button_hy EQU 500  ;;buton afisare harta
button_hsize EQU 100

button_x EQU 400
button_y EQU 100			;;tabela joc
button_xsize EQU 500
button_ysize EQU 400 ;dimensiunea laturii patratului
 
harta_x EQU 950
harta_y EQU 100       ;harta
harta_xsize EQU 500
harta_ysize EQU 400

miniB_x EQU 400
miniB_y EQU 100				;patratele din tabela
miniB_xsize EQU 50
miniB_ysize EQU 40

.code
; procedura make_text afiseaza o litera sau o cifra la coordonatele date
; arg1 - simbolul de afisat (litera sau cifra)
; arg2 - pointer la vectorul de pixeli
; arg3 - pos_x
; arg4 - pos_y
make_text proc
	push ebp
	mov ebp, esp
	pusha
	
	mov eax, [ebp+arg1] ; citim simbolul de afisat
	cmp eax, '!'
	je make_exclamation
	cmp eax, ':'
	je make_twopoints
	cmp eax, 'A'
	jl make_digit
	cmp eax, 'Z'
	jg make_digit
	sub eax, 'A'
	lea esi, letters
	jmp draw_text
make_digit:
	cmp eax, '0'
	jl make_space
	cmp eax, '9'
	jg make_space
	sub eax, '0'
	lea esi, digits
	jmp draw_text
make_space:	
	mov eax, 26 ; de la 0 pana la 25 sunt litere, 26 e space
	lea esi, letters
	jmp draw_text
make_exclamation:
	mov eax, 27
	lea esi, letters
	jmp draw_text
make_twopoints:
	mov eax, 28
	lea esi, letters
	jmp draw_text
	
	
draw_text:
	mov ebx, symbol_width
	mul ebx
	mov ebx, symbol_height
	mul ebx
	add esi, eax
	mov ecx, symbol_height
bucla_simbol_linii:
	mov edi, [ebp+arg2] ; pointer la matricea de pixeli
	mov eax, [ebp+arg4] ; pointer la coord y
	add eax, symbol_height
	sub eax, ecx
	mov ebx, area_width
	mul ebx
	add eax, [ebp+arg3] ; pointer la coord x
	shl eax, 2 ; inmultim cu 4, avem un DWORD per pixel
	add edi, eax
	push ecx
	mov ecx, symbol_width
bucla_simbol_coloane:
	cmp byte ptr [esi], 0
	je simbol_pixel_alb
	mov dword ptr [edi], 0
	jmp simbol_pixel_next
simbol_pixel_alb:
	mov dword ptr [edi], 0FFFFFFh
simbol_pixel_next:
	inc esi
	add edi, 4
	loop bucla_simbol_coloane
	pop ecx
	loop bucla_simbol_linii
	popa
	mov esp, ebp
	pop ebp
	ret
make_text endp

; un macro ca sa apelam mai usor desenarea simbolului
make_text_macro macro symbol, drawArea, x, y
	push y
	push x
	push drawArea
	push symbol
	call make_text
	add esp, 16
endm


;;;;;;;;;;;macro pt colorare
make_culoare proc
	push ebp
	mov ebp, esp
	pusha
	
	mov eax, [ebp+arg1] ; citim simbolul de afisat
	lea esi, culoare
draw_culoare:
	mov ebx, color_width
	mul ebx
	mov ebx, color_height
	mul ebx
	add esi, eax
	mov ecx, color_height
bucla_simbol_linii:
	mov edi, [ebp+arg2] ; pointer la matricea de pixeli
	mov eax, [ebp+arg4] ; pointer la coord y
	add eax, color_height
	sub eax, ecx
	mov ebx, area_width
	mul ebx
	add eax, [ebp+arg3] ; pointer la coord x
	shl eax, 2 ; inmultim cu 4, avem un DWORD per pixel
	add edi, eax
	push ecx
	mov ecx, color_width
bucla_simbol_coloane:
	cmp byte ptr [esi], 0
	je simbol_pixel_alb
	mov eax, [ebp + arg1]
	cmp eax, 2
	je desen_alb
	mov dword ptr [edi], 0ff0a0ah
	jmp simbol_pixel_next
desen_alb:
	mov dword ptr [edi], 0FFFFFFh
	jmp simbol_pixel_next
simbol_pixel_alb:
	mov dword ptr [edi], 06962cch
simbol_pixel_next:
	inc esi
	add edi, 4
	loop bucla_simbol_coloane
	pop ecx
	loop bucla_simbol_linii
	popa
	mov esp, ebp
	pop ebp
	ret
make_culoare endp

; un macro ca sa apelam mai usor desenarea simbolului
make_culoare_macro macro symbol, drawArea, x, y
	push y
	push x
	push drawArea
	push symbol
	call make_culoare
	add esp, 16
endm

line_horizontal macro x, y, len, color
local bucla_line
	mov eax, y        ;EAX=y 
	mov ebx,  area_width      
	mul ebx          ; EAX= y*area_width
	add eax, x      ;EAX = y*area_width + x
	shl eax, 2       ;EAX = (y*area_width + x)*4
	add eax, area  
	mov ecx, len
bucla_line:
	mov dword ptr[eax], color
	add eax, 4
	loop bucla_line
endm


line_vertical macro x, y, len, color
local bucla_line
	mov eax, y        ;EAX=y 
	mov ebx,  area_width      
	mul ebx          ; EAX= y*area_width
	add eax, x      ;EAX = y*area_width + x
	shl eax, 2       ;EAX = (y*area_width + x)*4
	add eax, area  
	mov ecx, len
bucla_line:
	mov dword ptr[eax], color
	add eax, 4*area_width
	loop bucla_line
endm
	

; culorare_buton macro x, y, len0, color
; local bucla_buton
	; mov eax, y        ;EAX=y 
	; mov ebx,  area_width      
	; mul ebx          ; EAX= y*area_width
	; add eax, x      ;EAX = y*area_width + x
	; shl eax, 2       ;EAX = (y*area_width + x)*4
	; mov edx, eax
	; add eax, area              ;ca sa scriem ceva in vector 
	; mov ecx, len0
	; sub ecx, edx
; bucla_buton:
	; mov dword ptr[eax], color
	; add eax, 4
	; loop bucla_buton

; endm	
	


; functia de desenare - se apeleaza la fiecare click
; sau la fiecare interval de 200ms in care nu s-a dat click
; arg1 - evt (0 - initializare, 1 - click, 2 - s-a scurs intervalul fara click)
; arg2 - x  
; arg3 - y

draw proc ;scriem cod cu care desenam si reactionam la evenimente
	push ebp
	mov ebp, esp
	pusha
	
	cmp parti, 0
	je afisare_litere
	
	; verifica argumentul 1 care e tipul de eveniment
	mov eax, [ebp+arg1]  ; am citit de pe stiva primul argument
	cmp eax, 1
	jz evt_click ; am avut un ev de click si sarim acolo
	cmp eax, 2
	jz evt_timer ; nu s-a efectuat click pe nimic
	;daca nu e nici 1 si nici 2 inseamna ca e 0 deci trebuie sa initializam zona
	;mai jos e codul care intializeaza fereastra cu pixeli albi
	mov eax, area_width
	mov ebx, area_height
	mul ebx
	shl eax, 2
	push eax
	push 255
	push area
	call memset ;care umple cu 255 (pixeli albi peste tot )
	add esp, 12
	;jmp afisare_litere
	
evt_click:
	; mov eax, [ebp+arg3]        ;EAX=y 
	; mov ebx,  area_width       ;pt ca nu se poate inmulti cu o constanta
	; mul ebx                    ; EAX= y*area_width
	; add eax, [ebp+arg2]        ;EAX = y*area_width + x
	; shl eax, 2                 ;EAX = (y*area_width + x)*4
	; add eax, area              ;ca sa scriem ceva in vector 
	; mov dword ptr[eax], 0FF0000h     ;colorare pixel
    ; mov dword ptr[eax+4], 0FF0000h   ;pixel dreapta
	; mov dword ptr[eax-4], 0FF0000h   ;pixel stanga
	; mov dword ptr[eax+4*area_width], 0FF0000h        ;pixel jos
	; mov dword ptr[eax-4*area_width], 0FF0000h        ;pixel sus
	;line_horizontal [ebp+arg2], [ebp+arg3], 30, 0FFh
	;line_vertical [ebp+arg2], [ebp+arg3], 30, 0FF00h
	
	; mov eax, [ebp+arg2]
	; cmp eax, button_x
	; jl  button_fail
	; cmp eax, button_x + button_size
	; jg button_fail
	
	; mov eax, [ebp+arg3]
	; cmp eax, button_y
	; jl  button_fail
	; cmp eax, button_y + button_size
	; jg button_fail
	
	
	;;;;;;;verificare buton harta  
	buton_afisareH:
	mov eax, [ebp+arg2]
	cmp eax, button_hx
	jl  buton11
	cmp eax, button_hx + button_hsize
	jg buton11
	
	mov eax, [ebp+arg3]
	cmp eax, button_hy
	jl  buton11
	cmp eax, button_hy + button_hsize
	jg buton11
	
	; make_culoare_macro 0,  area, button_hx , button_hy
	; make_culoare_macro 0,  area, button_hx + 40, button_hy 
	; make_culoare_macro 0,  area, button_hx + 52 , button_hy 
	; make_culoare_macro 0,  area, button_hx , button_hy +30
	; make_culoare_macro 0,  area, button_hx + 40, button_hy +30
	; make_culoare_macro 0,  area, button_hx + 52 , button_hy +30
	; make_culoare_macro 0,  area, button_hx , button_hy +61
	; make_culoare_macro 0,  area, button_hx + 40, button_hy +61
	; make_culoare_macro 0,  area, button_hx + 52 , button_hy +61
	
	make_text_macro 'A', area, button_hx + button_hsize/2 -40, button_hy + button_hsize + 10
	make_text_macro 'F', area, button_hx + button_hsize/2 -30, button_hy + button_hsize + 10
	make_text_macro 'I', area, button_hx + button_hsize/2 -20, button_hy + button_hsize + 10
	make_text_macro 'S', area, button_hx + button_hsize/2 -10, button_hy + button_hsize + 10
	make_text_macro 'A', area, button_hx + button_hsize/2, button_hy + button_hsize + 10
	make_text_macro 'T', area, button_hx + button_hsize/2 +10, button_hy + button_hsize + 10
	make_text_macro 'A', area, button_hx + button_hsize/2 +20, button_hy + button_hsize + 10
	make_text_macro '!', area, button_hx + button_hsize/2 +30, button_hy + button_hsize + 10
	mov counterOK, 0  ;;resetem counter-ul
	
	make_text_macro 'H', area, harta_x + harta_xsize/2 -20 , harta_y -20 
	make_text_macro 'A', area, harta_x + harta_xsize/2 -10, harta_y -20
	make_text_macro 'R', area, harta_x + harta_xsize/2, harta_y -20
	make_text_macro 'T', area, harta_x + harta_xsize/2 +10, harta_y - 20
	make_text_macro 'A', area, harta_x + harta_xsize/2 +20, harta_y - 20
	
	line_horizontal harta_x, harta_y, harta_xsize, 0
	line_horizontal harta_x, harta_y + harta_ysize, harta_xsize, 0
	line_vertical harta_x, harta_y, harta_ysize, 0
	line_vertical harta_x + harta_xsize, harta_y, harta_ysize, 0
	
	mov counterHarta, 0
	
	lea esi, Table_game
	mov y, 101
	mov ebx, 0
	for1:
		push ebx
		mov ecx, 0
		mov x, 951
		for2:
			push ecx
			mov edx, 0
			mov dl, [esi + ecx]
			make_culoare_macro edx, area, x, y
			pop ecx
			inc ecx
			add x, 50
			cmp ecx, 10
		jb for2
		add esi, 10
		pop ebx
		inc ebx
		add y, 40
		cmp ebx, 10
	jb for1
	
	jmp afisare_litere  ;; ca sa sara peste button_fail si timer
	
	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;linia1
	
	;;;;;;;;;verificare buton[1][1]
	buton11:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x
	jle  buton12
	cmp eax, miniB_x + miniB_xsize
	jge buton12
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y
	jle  buton12
	cmp eax, miniB_y + miniB_ysize
	jge buton12
	
	lea esi, Table_game
	mov ebx, 0
	mov bl, [esi]
	
	make_culoare_macro ebx, area, miniB_x + 1, miniB_y + 1
	add vector[0], 1
	cmp vector[0], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari11
	cmp ebx, 1
	je inc_succes11
	inc_ratari11:
		inc ratari
		jmp colorare11
	inc_succes11:
		inc succes
		dec parti
		jmp colorare11
	colorare11:
	jmp afisare_litere
	
	
	;;;;;;;;;verificare buton[1][2]
	buton12:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 50
	jle  buton13
	cmp eax, miniB_x + miniB_xsize + 50
	jge buton13
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y
	jle  buton13
	cmp eax, miniB_y + miniB_ysize
	jge buton13
	
	lea esi, Table_game
	mov ebx, 0
	mov bl, [esi+1]
	
	make_culoare_macro ebx, area, miniB_x + 51, miniB_y + 1
	add vector[1], 1
	cmp vector[1], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari12
	cmp ebx, 1
	je inc_succes12
	inc_ratari12:
		inc ratari
		jmp colorare12
	inc_succes12:
		inc succes
		dec parti
		jmp colorare12
	colorare12:
	jmp afisare_litere
	
	
	;;;;;;;;;verificare buton[1][3]
	buton13:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 100
	jle  buton14
	cmp eax, miniB_x + miniB_xsize + 100
	jge buton14
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y
	jle  buton14
	cmp eax, miniB_y + miniB_ysize
	jge buton14
	
	lea esi, Table_game
	mov ebx, 0
	mov bl, [esi+2]
	
	make_culoare_macro ebx, area, miniB_x + 101, miniB_y + 1
	add vector[2], 1
	cmp vector[2], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari13
	cmp ebx, 1
	je inc_succes13
	inc_ratari13:
		inc ratari
		jmp colorare13
	inc_succes13:
		inc succes
		dec parti
		jmp colorare13
	colorare13:
	jmp afisare_litere
	
	
	;;;;;;;;;verificare buton[1][4]
	buton14:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 150
	jle  buton15
	cmp eax, miniB_x + miniB_xsize + 150
	jge buton15
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y
	jle  buton15
	cmp eax, miniB_y + miniB_ysize
	jge buton15
	
	lea esi, Table_game
	mov ebx, 0
	mov bl, [esi+3]
	
	make_culoare_macro ebx, area, miniB_x + 151, miniB_y + 1
	add vector[3], 1
	cmp vector[3], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari14
	cmp ebx, 1
	je inc_succes14
	inc_ratari14:
		inc ratari
		jmp colorare14
	inc_succes14:
		inc succes
		dec parti
		jmp colorare14
	colorare14:
	jmp afisare_litere
	
	
	;;;;;;;;;verificare buton[1][5]
	buton15:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 200
	jle  buton16
	cmp eax, miniB_x + miniB_xsize + 200
	jge buton16
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y
	jle  buton16
	cmp eax, miniB_y + miniB_ysize
	jge buton16
	
	lea esi, Table_game
	mov ebx, 0
	mov bl, [esi+4]
	
	make_culoare_macro ebx, area, miniB_x + 201, miniB_y + 1
	add vector[4], 1
	cmp vector[4], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari15
	cmp ebx, 1
	je inc_succes15
	inc_ratari15:
		inc ratari
		jmp colorare15
	inc_succes15:
		inc succes
		dec parti
		jmp colorare15
	colorare15:
	jmp afisare_litere
	
	
	;;;;;;;;;verificare buton[1][6]
	buton16:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 250
	jle  buton17
	cmp eax, miniB_x + miniB_xsize + 250
	jge buton17
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y
	jle  buton17
	cmp eax, miniB_y + miniB_ysize
	jge buton17
	
	lea esi, Table_game
	mov ebx, 0
	mov bl, [esi+5]
	
	make_culoare_macro ebx, area, miniB_x + 251, miniB_y + 1
	add vector[5], 1
	cmp vector[5], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari16
	cmp ebx, 1
	je inc_succes16
	inc_ratari16:
		inc ratari
		jmp colorare16
	inc_succes16:
		inc succes
		dec parti
		jmp colorare16
	colorare16:
	jmp afisare_litere
	
	
	;;;;;;;;;verificare buton[1][7]
	buton17:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 300
	jle  buton18
	cmp eax, miniB_x + miniB_xsize + 300
	jge buton18
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y
	jle  buton18
	cmp eax, miniB_y + miniB_ysize
	jge buton18
	
	lea esi, Table_game
	mov ebx, 0
	mov bl, [esi+6]
	
	make_culoare_macro ebx, area, miniB_x + 301, miniB_y + 1
	add vector[6], 1
	cmp vector[6], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari17
	cmp ebx, 1
	je inc_succes17
	inc_ratari17:
		inc ratari
		jmp colorare17
	inc_succes17:
		inc succes
		dec parti
		jmp colorare17
	colorare17:
	jmp afisare_litere
	
	
	;;;;;;;;;verificare buton[1][8]
	buton18:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 350
	jle  buton19
	cmp eax, miniB_x + miniB_xsize + 350
	jge buton19
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y
	jle  buton19
	cmp eax, miniB_y + miniB_ysize
	jge buton19
	
	lea esi, Table_game
	mov ebx, 0
	mov bl, [esi+7]
	
	make_culoare_macro ebx, area, miniB_x + 351, miniB_y + 1
	add vector[7], 1
	cmp vector[7], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari18
	cmp ebx, 1
	je inc_succes18
	inc_ratari18:
		inc ratari
		jmp colorare18
	inc_succes18:
		inc succes
		dec parti
		jmp colorare18
	colorare18:
	jmp afisare_litere
	
	
	;;;;;;;;;verificare buton[1][9]
	buton19:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 400
	jle  buton110
	cmp eax, miniB_x + miniB_xsize + 400
	jge buton110
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y
	jle  buton110
	cmp eax, miniB_y + miniB_ysize
	jge buton110
	
	lea esi, Table_game
	mov ebx, 0
	mov bl, [esi+8]
	
	make_culoare_macro ebx, area, miniB_x + 401, miniB_y + 1
	add vector[8], 1
	cmp vector[8], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari19
	cmp ebx, 1
	je inc_succes19
	inc_ratari19:
		inc ratari
		jmp colorare19
	inc_succes19:
		inc succes
		dec parti
		jmp colorare19
	colorare19:
	jmp afisare_litere
	
	
	;;;;;;;;;verificare buton[1][10]
	buton110:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 450
	jle  buton21
	cmp eax, miniB_x + miniB_xsize + 450
	jge buton21
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y
	jle  buton21
	cmp eax, miniB_y + miniB_ysize
	jge buton21

	lea esi, Table_game
	mov ebx, 0
	mov bl, [esi+9]
	
	make_culoare_macro ebx, area, miniB_x + 451, miniB_y + 1
	add vector[9], 1
	cmp vector[9], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari110
	cmp ebx, 1
	je inc_succes110
	inc_ratari110:
		inc ratari
		jmp colorare110
	inc_succes110:
		inc succes
		dec parti
		jmp colorare110
	colorare110:
	jmp afisare_litere
	
	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;linia2
	
	;;;;;;;;;verificare buton[2][1]
	buton21:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x
	jle  buton22
	cmp eax, miniB_x + miniB_xsize
	jge buton22
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 40
	jle  buton22
	cmp eax, miniB_y + miniB_ysize + 40
	jge buton22
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 10
	mov bl, [esi]
	
	make_culoare_macro ebx, area, miniB_x + 1, miniB_y + 40 + 1
	add vector[10], 1
	cmp vector[10], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari21
	cmp ebx, 1
	je inc_succes21
	inc_ratari21:
		inc ratari
		jmp colorare21
	inc_succes21:
		inc succes
		dec parti
		jmp colorare21
	colorare21:
	jmp afisare_litere
	
	
	;;;;;;;;;verificare buton[2][2]
	buton22:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 50
	jle  buton23
	cmp eax, miniB_x + miniB_xsize + 50
	jge buton23
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 40
	jle  buton23
	cmp eax, miniB_y + miniB_ysize + 40
	jge buton23
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 10
	mov bl, [esi+1]
	
	make_culoare_macro ebx, area, miniB_x + 51, miniB_y + 40 + 1
	add vector[11], 1
	cmp vector[11], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari22
	cmp ebx, 1
	je inc_succes22
	inc_ratari22:
		inc ratari
		jmp colorare22
	inc_succes22:
		inc succes
		dec parti
		jmp colorare22
	colorare22:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[2][3]
	buton23:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 100
	jle  buton24
	cmp eax, miniB_x + miniB_xsize + 100
	jge buton24
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 40
	jle  buton24
	cmp eax, miniB_y + miniB_ysize + 40
	jge buton24
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 10
	mov bl, [esi+2]
	
	make_culoare_macro ebx, area, miniB_x + 101, miniB_y + 40 + 1
	add vector[12], 1
	cmp vector[12], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari23
	cmp ebx, 1
	je inc_succes23
	inc_ratari23:
		inc ratari
		jmp colorare23
	inc_succes23:
		inc succes
		dec parti
		jmp colorare23
	colorare23:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[2][4]
	buton24:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 150
	jle  buton25
	cmp eax, miniB_x + miniB_xsize + 150
	jge buton25
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 40
	jle  buton25
	cmp eax, miniB_y + miniB_ysize + 40
	jge buton25
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 10
	mov bl, [esi+3]
	
	make_culoare_macro ebx, area, miniB_x + 151, miniB_y + 40 + 1
	add vector[13], 1
	cmp vector[13], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari24
	cmp ebx, 1
	je inc_succes24
	inc_ratari24:
		inc ratari
		jmp colorare24
	inc_succes24:
		inc succes
		dec parti
		jmp colorare24
	colorare24:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[2][5]
	buton25:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 200
	jle  buton26
	cmp eax, miniB_x + miniB_xsize + 200
	jge buton26
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 40
	jle  buton26
	cmp eax, miniB_y + miniB_ysize + 40
	jge buton26
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 10
	mov bl, [esi+4]
	
	make_culoare_macro ebx, area, miniB_x + 201, miniB_y + 40 + 1
	add vector[14], 1
	cmp vector[14], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari25
	cmp ebx, 1
	je inc_succes25
	inc_ratari25:
		inc ratari
		jmp colorare25
	inc_succes25:
		inc succes
		dec parti
		jmp colorare25
	colorare25:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[2][6]
	buton26:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 250
	jle  buton27
	cmp eax, miniB_x + miniB_xsize + 250
	jge buton27
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 40
	jle  buton27
	cmp eax, miniB_y + miniB_ysize + 40
	jge buton27
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 10
	mov bl, [esi+5]
	
	make_culoare_macro ebx, area, miniB_x + 251, miniB_y + 40 + 1
	add vector[15], 1
	cmp vector[15], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari26
	cmp ebx, 1
	je inc_succes26
	inc_ratari26:
		inc ratari
		jmp colorare26
	inc_succes26:
		inc succes
		dec parti
		jmp colorare26
	colorare26:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[2][7]
	buton27:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 300
	jle  buton28
	cmp eax, miniB_x + miniB_xsize + 300
	jge buton28
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 40
	jle  buton28
	cmp eax, miniB_y + miniB_ysize + 40
	jge buton28
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 10
	mov bl, [esi+6]
	
	make_culoare_macro ebx, area, miniB_x + 301, miniB_y + 40 + 1
	add vector[16], 1
	cmp vector[16], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari27
	cmp ebx, 1
	je inc_succes27
	inc_ratari27:
		inc ratari
		jmp colorare27
	inc_succes27:
		inc succes
		dec parti
		jmp colorare27
	colorare27:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[2][8]
	buton28:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 350
	jle  buton29
	cmp eax, miniB_x + miniB_xsize + 350
	jge buton29
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 40
	jle  buton29
	cmp eax, miniB_y + miniB_ysize + 40
	jge buton29
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 10
	mov bl, [esi+7]
	
	make_culoare_macro ebx, area, miniB_x + 351, miniB_y + 40 + 1
	add vector[17], 1
	cmp vector[17], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari28
	cmp ebx, 1
	je inc_succes28
	inc_ratari28:
		inc ratari
		jmp colorare28
	inc_succes28:
		inc succes
		dec parti
		jmp colorare28
	colorare28:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[2][9]
	buton29:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 400
	jle  buton210
	cmp eax, miniB_x + miniB_xsize + 400
	jge buton210
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 40
	jle  buton210
	cmp eax, miniB_y + miniB_ysize + 40
	jge buton210
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 10
	mov bl, [esi+8]
	
	make_culoare_macro ebx, area, miniB_x + 401, miniB_y + 40 + 1
	add vector[18], 1
	cmp vector[18], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari29
	cmp ebx, 1
	je inc_succes29
	inc_ratari29:
		inc ratari
		jmp colorare29
	inc_succes29:
		inc succes
		dec parti
		jmp colorare29
	colorare29:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[2][10]
	buton210:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 450
	jle  buton31
	cmp eax, miniB_x + miniB_xsize + 450
	jge buton31
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 40
	jle  buton31
	cmp eax, miniB_y + miniB_ysize + 40
	jge buton31
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 20
	mov bl, [esi+9]
	
	make_culoare_macro ebx, area, miniB_x + 451, miniB_y + 40 + 1
	add vector[19], 1
	cmp vector[19], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari210
	cmp ebx, 1
	je inc_succes210
	inc_ratari210:
		inc ratari
		jmp colorare210
	inc_succes210:
		inc succes
		dec parti
		jmp colorare210
	colorare210:
	jmp afisare_litere

	
	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;linia3
	
	
	;;;;;;;;;verificare buton[3][1]
	buton31:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x
	jle  buton32
	cmp eax, miniB_x + miniB_xsize
	jge buton32
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 80
	jle  buton32
	cmp eax, miniB_y + miniB_ysize + 80
	jge buton32
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 20
	mov bl, [esi]
	
	make_culoare_macro ebx, area, miniB_x + 1, miniB_y + 80 + 1
	add vector[20], 1
	cmp vector[20], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari31
	cmp ebx, 1
	je inc_succes31
	inc_ratari31:
		inc ratari
		jmp colorare31
	inc_succes31:
		inc succes
		dec parti
		jmp colorare31
	colorare31:
	jmp afisare_litere
	
	
	;;;;;;;;;verificare buton[3][2]
	buton32:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 50
	jle  buton33
	cmp eax, miniB_x + miniB_xsize + 50
	jge buton33
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 80
	jle  buton33
	cmp eax, miniB_y + miniB_ysize + 80
	jge buton33
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 20
	mov bl, [esi+1]
	
	make_culoare_macro ebx, area, miniB_x + 51, miniB_y + 80 + 1
	add vector[21], 1
	cmp vector[21], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari32
	cmp ebx, 1
	je inc_succes32
	inc_ratari32:
		inc ratari
		jmp colorare32
	inc_succes32:
		inc succes
		dec parti
		jmp colorare32
	colorare32:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[3][3]
	buton33:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 100
	jle  buton34
	cmp eax, miniB_x + miniB_xsize + 100
	jge buton34
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 80
	jle  buton34
	cmp eax, miniB_y + miniB_ysize + 80
	jge buton34
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 20
	mov bl, [esi+2]
	
	make_culoare_macro ebx, area, miniB_x + 101, miniB_y + 80 + 1
	add vector[22], 1
	cmp vector[22], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari33
	cmp ebx, 1
	je inc_succes33
	inc_ratari33:
		inc ratari
		jmp colorare33
	inc_succes33:
		inc succes
		dec parti
		jmp colorare33
	colorare33:

	jmp afisare_litere
	
	
	;;;;;;;verificare buton[3][4]
	buton34:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 150
	jle  buton35
	cmp eax, miniB_x + miniB_xsize + 150
	jge buton35
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 80
	jle  buton35
	cmp eax, miniB_y + miniB_ysize + 80
	jge buton35
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 20
	mov bl, [esi+3]
	
	make_culoare_macro ebx, area, miniB_x + 151, miniB_y + 80 + 1
	add vector[23], 1
	cmp vector[23], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari34
	cmp ebx, 1
	je inc_succes34
	inc_ratari34:
		inc ratari
		jmp colorare34
	inc_succes34:
		inc succes
		dec parti
		jmp colorare34
	colorare34:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[3][5]
	buton35:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 200
	jle  buton36
	cmp eax, miniB_x + miniB_xsize + 200
	jge buton36
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 80
	jle  buton36
	cmp eax, miniB_y + miniB_ysize + 80
	jge buton36
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 20
	mov bl, [esi+4]
	
	make_culoare_macro ebx, area, miniB_x + 201, miniB_y + 80 + 1
	add vector[24], 1
	cmp vector[24], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari35
	cmp ebx, 1
	je inc_succes35
	inc_ratari35:
		inc ratari
		jmp colorare35
	inc_succes35:
		inc succes
		dec parti
		jmp colorare35
	colorare35:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[3][6]
	buton36:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 250
	jle  buton37
	cmp eax, miniB_x + miniB_xsize + 250
	jge buton37
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 80
	jle  buton37
	cmp eax, miniB_y + miniB_ysize + 80
	jge buton37
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 20
	mov bl, [esi+5]
	
	make_culoare_macro ebx, area, miniB_x + 251, miniB_y + 80 + 1
	add vector[25], 1
	cmp vector[25], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari36
	cmp ebx, 1
	je inc_succes36
	inc_ratari36:
		inc ratari
		jmp colorare36
	inc_succes36:
		inc succes
		dec parti
		jmp colorare36
	colorare36:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[3][7]
	buton37:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 300
	jle  buton38
	cmp eax, miniB_x + miniB_xsize + 300
	jge buton38
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 80
	jle  buton38
	cmp eax, miniB_y + miniB_ysize + 80
	jge buton38
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 20
	mov bl, [esi+6]
	
	make_culoare_macro ebx, area, miniB_x + 301, miniB_y + 80 + 1
	add vector[26], 1
	cmp vector[26], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari37
	cmp ebx, 1
	je inc_succes37
	inc_ratari37:
		inc ratari
		jmp colorare37
	inc_succes37:
		inc succes
		dec parti
		jmp colorare37
	colorare37:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[3][8]
	buton38:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 350
	jle  buton39
	cmp eax, miniB_x + miniB_xsize + 350
	jge buton39
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 80
	jle  buton39
	cmp eax, miniB_y + miniB_ysize + 80
	jge buton39
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 20
	mov bl, [esi+7]
	
	make_culoare_macro ebx, area, miniB_x + 351, miniB_y + 80 + 1
	add vector[27], 1
	cmp vector[27], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari38
	cmp ebx, 1
	je inc_succes38
	inc_ratari38:
		inc ratari
		jmp colorare38
	inc_succes38:
		inc succes
		dec parti
		jmp colorare38
	colorare38:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[3][9]
	buton39:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 400
	jle  buton310
	cmp eax, miniB_x + miniB_xsize + 400
	jge buton310
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 80
	jle  buton310
	cmp eax, miniB_y + miniB_ysize + 80
	jge buton310
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 20
	mov bl, [esi+8]
	
	make_culoare_macro ebx, area, miniB_x + 401, miniB_y + 80 + 1
	add vector[28], 1
	cmp vector[28], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari39
	cmp ebx, 1
	je inc_succes39
	inc_ratari39:
		inc ratari
		jmp colorare39
	inc_succes39:
		inc succes
		dec parti
		jmp colorare39
	colorare39:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[3][10]
	buton310:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 450
	jle  buton41
	cmp eax, miniB_x + miniB_xsize + 450
	jge buton41
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 80
	jle  buton41
	cmp eax, miniB_y + miniB_ysize + 80
	jge buton41
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 20
	mov bl, [esi+9]
	
	make_culoare_macro ebx, area, miniB_x + 451, miniB_y + 80 + 1
	add vector[29], 1
	cmp vector[29], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari310
	cmp ebx, 1
	je inc_succes310
	inc_ratari310:
		inc ratari
		jmp colorare310
	inc_succes310:
		inc succes
		dec parti
		jmp colorare310
	colorare310:
	jmp afisare_litere

	
	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;linia4
	
	;;;;;;;;;verificare buton[4][1]
	buton41:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x
	jle  buton42
	cmp eax, miniB_x + miniB_xsize
	jge buton42
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 120
	jle  buton42
	cmp eax, miniB_y + miniB_ysize + 120
	jge buton42
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 30
	mov bl, [esi]
	
	make_culoare_macro ebx, area, miniB_x + 1, miniB_y + 120 + 1
	add vector[30], 1
	cmp vector[30], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari41
	cmp ebx, 1
	je inc_succes41
	inc_ratari41:
		inc ratari
		jmp colorare41
	inc_succes41:
		inc succes
		dec parti
		jmp colorare41
	colorare41:
	jmp afisare_litere
	
	
	;;;;;;;;;verificare buton[4][2]
	buton42:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 50
	jle  buton43
	cmp eax, miniB_x + miniB_xsize + 50
	jge buton43
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 120
	jle  buton43
	cmp eax, miniB_y + miniB_ysize + 120
	jge buton43
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 30
	mov bl, [esi+1]
	
	make_culoare_macro ebx, area, miniB_x + 51, miniB_y + 120 + 1
	add vector[31], 1
	cmp vector[31], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari42
	cmp ebx, 1
	je inc_succes42
	inc_ratari42:
		inc ratari
		jmp colorare42
	inc_succes42:
		inc succes
		dec parti
		jmp colorare42
	colorare42:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[4][3]
	buton43:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 100
	jle  buton44
	cmp eax, miniB_x + miniB_xsize + 100
	jge buton44
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 120
	jle  buton44
	cmp eax, miniB_y + miniB_ysize + 120
	jge buton44
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 30
	mov bl, [esi+2]
	
	make_culoare_macro ebx, area, miniB_x + 101, miniB_y + 120 + 1
	add vector[32], 1
	cmp vector[32], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari43
	cmp ebx, 1
	je inc_succes43
	inc_ratari43:
		inc ratari
		jmp colorare43
	inc_succes43:
		inc succes
		dec parti
		jmp colorare43
	colorare43:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[4][4]
	buton44:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 150
	jle  buton45
	cmp eax, miniB_x + miniB_xsize + 150
	jge buton45
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 120
	jle  buton45
	cmp eax, miniB_y + miniB_ysize + 120
	jge buton45
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 30
	mov bl, [esi+3]
	
	make_culoare_macro ebx, area, miniB_x + 151, miniB_y + 120 + 1
	add vector[33], 1
	cmp vector[33], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari44
	cmp ebx, 1
	je inc_succes44
	inc_ratari44:
		inc ratari
		jmp colorare44
	inc_succes44:
		inc succes
		dec parti
		jmp colorare44
	colorare44:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[4][5]
	buton45:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 200
	jle  buton46
	cmp eax, miniB_x + miniB_xsize + 200
	jge buton46
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 120
	jle  buton46
	cmp eax, miniB_y + miniB_ysize + 120
	jge buton46
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 30
	mov bl, [esi+4]
	
	make_culoare_macro ebx, area, miniB_x + 201, miniB_y + 120 + 1
	add vector[34], 1
	cmp vector[34], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari45
	cmp ebx, 1
	je inc_succes45
	inc_ratari45:
		inc ratari
		jmp colorare45
	inc_succes45:
		inc succes
		dec parti
		jmp colorare45
	colorare45:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[4][6]
	buton46:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 250
	jle  buton47
	cmp eax, miniB_x + miniB_xsize + 250
	jge buton47
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 120
	jle  buton47
	cmp eax, miniB_y + miniB_ysize + 120
	jge buton47
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 30
	mov bl, [esi+5]
	
	make_culoare_macro ebx, area, miniB_x + 251, miniB_y + 120 + 1
	add vector[35], 1
	cmp vector[35], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari46
	cmp ebx, 1
	je inc_succes46
	inc_ratari46:
		inc ratari
		jmp colorare46
	inc_succes46:
		inc succes
		dec parti
		jmp colorare46
	colorare46:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[4][7]
	buton47:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 300
	jle  buton48
	cmp eax, miniB_x + miniB_xsize + 300
	jge buton48
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 120
	jle  buton48
	cmp eax, miniB_y + miniB_ysize + 120
	jge buton48
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 30
	mov bl, [esi+6]
	
	make_culoare_macro ebx, area, miniB_x + 301, miniB_y + 120 + 1
	add vector[36], 1
	cmp vector[36], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari47
	cmp ebx, 1
	je inc_succes47
	inc_ratari47:
		inc ratari
		jmp colorare47
	inc_succes47:
		inc succes
		dec parti
		jmp colorare47
	colorare47:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[4][8]
	buton48:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 350
	jle  buton49
	cmp eax, miniB_x + miniB_xsize + 350
	jge buton49
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 120
	jle  buton49
	cmp eax, miniB_y + miniB_ysize + 120
	jge buton49
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 30
	mov bl, [esi+7]
	
	make_culoare_macro ebx, area, miniB_x + 351, miniB_y + 120 + 1
	add vector[37], 1
	cmp vector[37], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari48
	cmp ebx, 1
	je inc_succes48
	inc_ratari48:
		inc ratari
		jmp colorare48
	inc_succes48:
		inc succes
		dec parti
		jmp colorare48
	colorare48:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[4][9]
	buton49:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 400
	jle  buton410
	cmp eax, miniB_x + miniB_xsize + 400
	jge buton410
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 120
	jle  buton410
	cmp eax, miniB_y + miniB_ysize + 120
	jge buton410
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 30
	mov bl, [esi+8]
	
	make_culoare_macro ebx, area, miniB_x + 401, miniB_y + 120 + 1
	add vector[38], 1
	cmp vector[38], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari49
	cmp ebx, 1
	je inc_succes49
	inc_ratari49:
		inc ratari
		jmp colorare49
	inc_succes49:
		inc succes
		dec parti
		jmp colorare49
	colorare49:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[4][10]
	buton410:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 450
	jle  buton51
	cmp eax, miniB_x + miniB_xsize + 450
	jge buton51
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 120
	jle  buton51
	cmp eax, miniB_y + miniB_ysize + 120
	jge buton51
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 30
	mov bl, [esi+9]
	
	make_culoare_macro ebx, area, miniB_x + 451, miniB_y + 120 + 1
	add vector[39], 1
	cmp vector[39], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari410
	cmp ebx, 1
	je inc_succes410
	inc_ratari410:
		inc ratari
		jmp colorare410
	inc_succes410:
		inc succes
		dec parti
		jmp colorare410
	colorare410:
	jmp afisare_litere

	
	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;linia5
	
	;;;;;;;;;verificare buton[5][1]
	buton51:
	cmp eax, miniB_x
	jle  buton52
	cmp eax, miniB_x + miniB_xsize
	jge buton52
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 160
	jle  buton52
	cmp eax, miniB_y + miniB_ysize + 160
	jge buton52
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 40
	mov bl, [esi]
	
	make_culoare_macro ebx, area, miniB_x + 1, miniB_y + 160 + 1
	add vector[40], 1
	cmp vector[40], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari51
	cmp ebx, 1
	je inc_succes51
	inc_ratari51:
		inc ratari
		jmp colorare51
	inc_succes51:
		inc succes
		dec parti
		jmp colorare51
	colorare51:
	jmp afisare_litere
	
	
	;;;;;;;;;verificare buton[5][2]
	buton52:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 50
	jle  buton53
	cmp eax, miniB_x + miniB_xsize + 50
	jge buton53
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 160
	jle  buton53
	cmp eax, miniB_y + miniB_ysize + 160
	jge buton53
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 40
	mov bl, [esi+1]
	
	make_culoare_macro ebx, area, miniB_x + 51, miniB_y + 160 + 1
	add vector[41], 1
	cmp vector[41], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari52
	cmp ebx, 1
	je inc_succes52
	inc_ratari52:
		inc ratari
		jmp colorare52
	inc_succes52:
		inc succes
		dec parti
		jmp colorare52
	colorare52:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[5][3]
	buton53:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 100
	jle  buton54
	cmp eax, miniB_x + miniB_xsize + 100
	jge buton54
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 160
	jle  buton54
	cmp eax, miniB_y + miniB_ysize + 160
	jge buton54
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 40
	mov bl, [esi+2]
	
	make_culoare_macro ebx, area, miniB_x + 101, miniB_y + 160 + 1
	add vector[42], 1
	cmp vector[42], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari53
	cmp ebx, 1
	je inc_succes53
	inc_ratari53:
		inc ratari
		jmp colorare53
	inc_succes53:
		inc succes
		dec parti
		jmp colorare53
	colorare53:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[5][4]
	buton54:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 150
	jle  buton55
	cmp eax, miniB_x + miniB_xsize + 150
	jge buton55
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 160
	jle  buton55
	cmp eax, miniB_y + miniB_ysize + 160
	jge buton55
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 40
	mov bl, [esi+3]
	
	make_culoare_macro ebx, area, miniB_x + 151, miniB_y + 160 + 1
	add vector[43], 1
	cmp vector[43], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari54
	cmp ebx, 1
	je inc_succes54
	inc_ratari54:
		inc ratari
		jmp colorare54
	inc_succes54:
		inc succes
		dec parti
		jmp colorare54
	colorare54:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[5][5]
	buton55:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 200
	jle  buton56
	cmp eax, miniB_x + miniB_xsize + 200
	jge buton56
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 160
	jle  buton56
	cmp eax, miniB_y + miniB_ysize + 160
	jge buton56
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 40
	mov bl, [esi+4]
	
	make_culoare_macro ebx, area, miniB_x + 201, miniB_y + 160 + 1
	add vector[44], 1
	cmp vector[44], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari55
	cmp ebx, 1
	je inc_succes55
	inc_ratari55:
		inc ratari
		jmp colorare55
	inc_succes55:
		inc succes
		dec parti
		jmp colorare55
	colorare55:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[5][6]
	buton56:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 250
	jle  buton57
	cmp eax, miniB_x + miniB_xsize + 250
	jge buton57
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 160
	jle  buton57
	cmp eax, miniB_y + miniB_ysize + 160
	jge buton57
	
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 40
	mov bl, [esi+5]
	
	make_culoare_macro ebx, area, miniB_x + 251, miniB_y + 160 + 1
	add vector[45], 1
	cmp vector[45], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari56
	cmp ebx, 1
	je inc_succes56
	inc_ratari56:
		inc ratari
		jmp colorare56
	inc_succes56:
		inc succes
		dec parti
		jmp colorare56
	colorare56:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[5][7]
	buton57:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 300
	jle  buton58
	cmp eax, miniB_x + miniB_xsize + 300
	jge buton58
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 160
	jle  buton58
	cmp eax, miniB_y + miniB_ysize + 160
	jge buton58
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 40
	mov bl, [esi+6]
	
	make_culoare_macro ebx, area, miniB_x + 301, miniB_y + 160 + 1
	add vector[46], 1
	cmp vector[46], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari57
	cmp ebx, 1
	je inc_succes57
	inc_ratari57:
		inc ratari
		jmp colorare57
	inc_succes57:
		inc succes
		dec parti
		jmp colorare57
	colorare57:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[5][8]
	buton58:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 350
	jle  buton59
	cmp eax, miniB_x + miniB_xsize + 350
	jge buton59
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 160
	jle  buton59
	cmp eax, miniB_y + miniB_ysize + 160
	jge buton59
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 40
	mov bl, [esi+7]
	
	make_culoare_macro ebx, area, miniB_x + 351, miniB_y + 160 + 1
	add vector[47], 1
	cmp vector[47], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari58
	cmp ebx, 1
	je inc_succes58
	inc_ratari58:
		inc ratari
		jmp colorare58
	inc_succes58:
		inc succes
		dec parti
		jmp colorare58
	colorare58:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[5][9]
	buton59:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 400
	jle  buton510
	cmp eax, miniB_x + miniB_xsize + 400
	jge buton510
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 160
	jle  buton510
	cmp eax, miniB_y + miniB_ysize + 160
	jge buton510
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 40
	mov bl, [esi+8]
	
	make_culoare_macro ebx, area, miniB_x + 401, miniB_y + 160 + 1
	add vector[48], 1
	cmp vector[48], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari59
	cmp ebx, 1
	je inc_succes59
	inc_ratari59:
		inc ratari
		jmp colorare59
	inc_succes59:
		inc succes
		dec parti
		jmp colorare59
	colorare59:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[5][10]
	buton510:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 450
	jle  buton61
	cmp eax, miniB_x + miniB_xsize + 450
	jge buton61
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 160
	jle  buton61
	cmp eax, miniB_y + miniB_ysize + 160
	jge buton61
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 40
	mov bl, [esi+9]
	
	make_culoare_macro ebx, area, miniB_x + 451, miniB_y + 160 + 1
	add vector[49], 1
	cmp vector[49], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari510
	cmp ebx, 1
	je inc_succes510
	inc_ratari510:
		inc ratari
		jmp colorare510
	inc_succes510:
		inc succes
		dec parti
		jmp colorare510
	colorare510:
	jmp afisare_litere
	
	
	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;linia6
	
	;;;;;;;;;verificare buton[6][1]
	buton61:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x
	jle  buton62
	cmp eax, miniB_x + miniB_xsize
	jge buton62
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 200
	jle  buton62
	cmp eax, miniB_y + miniB_ysize + 200
	jge buton62
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 50
	mov bl, [esi]
	
	make_culoare_macro ebx, area, miniB_x + 1, miniB_y + 200 + 1
	add vector[50], 1
	cmp vector[50], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari61
	cmp ebx, 1
	je inc_succes61
	inc_ratari61:
		inc ratari
		jmp colorare61
	inc_succes61:
		inc succes
		dec parti
		jmp colorare61
	colorare61:
	jmp afisare_litere
	
	
	;;;;;;;;;verificare buton[6][2]
	buton62:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 50
	jle  buton63
	cmp eax, miniB_x + miniB_xsize + 50
	jge buton63
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 200
	jle  buton63
	cmp eax, miniB_y + miniB_ysize + 200
	jge buton63
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 50
	mov bl, [esi+1]
	
	make_culoare_macro ebx, area, miniB_x + 51, miniB_y + 200 + 1
	add vector[51], 1
	cmp vector[51], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari62
	cmp ebx, 1
	je inc_succes62
	inc_ratari62:
		inc ratari
		jmp colorare62
	inc_succes62:
		inc succes
		dec parti
		jmp colorare62
	colorare62:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[6][3]
	buton63:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 100
	jle  buton64
	cmp eax, miniB_x + miniB_xsize + 100
	jge buton64
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 200
	jle  buton64
	cmp eax, miniB_y + miniB_ysize + 200
	jge buton64
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 50
	mov bl, [esi+2]
	
	make_culoare_macro ebx, area, miniB_x + 101, miniB_y + 200 + 1
	add vector[52], 1
	cmp vector[52], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari63
	cmp ebx, 1
	je inc_succes63
	inc_ratari63:
		inc ratari
		jmp colorare63
	inc_succes63:
		inc succes
		dec parti
		jmp colorare63
	colorare63:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[6][4]
	buton64:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 150
	jle  buton65
	cmp eax, miniB_x + miniB_xsize + 150
	jge buton65
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 200
	jle  buton65
	cmp eax, miniB_y + miniB_ysize + 200
	jge buton65
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 50
	mov bl, [esi+3]
	
	make_culoare_macro ebx, area, miniB_x + 151, miniB_y + 200 + 1
	add vector[53], 1
	cmp vector[53], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari64
	cmp ebx, 1
	je inc_succes64
	inc_ratari64:
		inc ratari
		jmp colorare64
	inc_succes64:
		inc succes
		dec parti
		jmp colorare64
	colorare64:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[6][5]
	buton65:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 200
	jle  buton66
	cmp eax, miniB_x + miniB_xsize + 200
	jge buton66
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 200
	jle  buton66
	cmp eax, miniB_y + miniB_ysize + 200
	jge buton66
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 50
	mov bl, [esi+4]
	
	make_culoare_macro ebx, area, miniB_x + 201, miniB_y + 200 + 1
	add vector[54], 1
	cmp vector[54], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari65
	cmp ebx, 1
	je inc_succes65
	inc_ratari65:
		inc ratari
		jmp colorare65
	inc_succes65:
		inc succes
		dec parti
		jmp colorare65
	colorare65:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[6][6]
	buton66:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 250
	jle  buton67
	cmp eax, miniB_x + miniB_xsize + 250
	jge buton67
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 200
	jle  buton67
	cmp eax, miniB_y + miniB_ysize + 200
	jge buton67
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 50
	mov bl, [esi+5]
	
	make_culoare_macro ebx, area, miniB_x + 251, miniB_y + 200 + 1
	add vector[55], 1
	cmp vector[55], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari66
	cmp ebx, 1
	je inc_succes66
	inc_ratari66:
		inc ratari
		jmp colorare66
	inc_succes66:
		inc succes
		dec parti
		jmp colorare66
	colorare66:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[6][7]
	buton67:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 300
	jle  buton68
	cmp eax, miniB_x + miniB_xsize + 300
	jge buton68
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 200
	jle  buton68
	cmp eax, miniB_y + miniB_ysize + 200
	jge buton68
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 50
	mov bl, [esi+6]
	
	make_culoare_macro ebx, area, miniB_x + 301, miniB_y + 200 + 1
	add vector[56], 1
	cmp vector[56], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari67
	cmp ebx, 1
	je inc_succes67
	inc_ratari67:
		inc ratari
		jmp colorare67
	inc_succes67:
		inc succes
		dec parti
		jmp colorare67
	colorare67:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[6][8]
	buton68:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 350
	jle  buton69
	cmp eax, miniB_x + miniB_xsize + 350
	jge buton69
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 200
	jle  buton69
	cmp eax, miniB_y + miniB_ysize + 200
	jge buton69
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 50
	mov bl, [esi+7]
	
	make_culoare_macro ebx, area, miniB_x + 351, miniB_y + 200 + 1
	add vector[57], 1
	cmp vector[57], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari68
	cmp ebx, 1
	je inc_succes68
	inc_ratari68:
		inc ratari
		jmp colorare68
	inc_succes68:
		inc succes
		dec parti
		jmp colorare68
	colorare68:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[6][9]
	buton69:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 400
	jle  buton610
	cmp eax, miniB_x + miniB_xsize + 400
	jge buton610
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 200
	jle  buton610
	cmp eax, miniB_y + miniB_ysize + 200
	jge buton610
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 50
	mov bl, [esi+8]
	
	make_culoare_macro ebx, area, miniB_x + 401, miniB_y + 200 + 1
	add vector[58], 1
	cmp vector[58], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari69
	cmp ebx, 1
	je inc_succes69
	inc_ratari69:
		inc ratari
		jmp colorare69
	inc_succes69:
		inc succes
		dec parti
		jmp colorare69
	colorare69:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[6][10]
	buton610:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 450
	jle  buton71
	cmp eax, miniB_x + miniB_xsize + 450
	jge buton71
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 200
	jle  buton71
	cmp eax, miniB_y + miniB_ysize + 200
	jge buton71
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 50
	mov bl, [esi+9]
	
	make_culoare_macro ebx, area, miniB_x + 451, miniB_y + 200 + 1
	add vector[59], 1
	cmp vector[59], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari610
	cmp ebx, 1
	je inc_succes610
	inc_ratari610:
		inc ratari
		jmp colorare610
	inc_succes610:
		inc succes
		dec parti
		jmp colorare610
	colorare610:
	jmp afisare_litere
	
	
	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;linia7
	
	;;;;;;;;;verificare buton[7][1]
	buton71:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x
	jle  buton72
	cmp eax, miniB_x + miniB_xsize
	jge buton72
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 240
	jle  buton72
	cmp eax, miniB_y + miniB_ysize + 240
	jge buton72
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 60
	mov bl, [esi]
	
	make_culoare_macro ebx, area, miniB_x + 1, miniB_y + 240 + 1
	add vector[60], 1
	cmp vector[60], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari71
	cmp ebx, 1
	je inc_succes71
	inc_ratari71:
		inc ratari
		jmp colorare71
	inc_succes71:
		inc succes
		dec parti
		jmp colorare71
	colorare71:
	jmp afisare_litere
	
	
	;;;;;;;;;verificare buton[7][2]
	buton72:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 50
	jle  buton73
	cmp eax, miniB_x + miniB_xsize + 50
	jge buton73
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 240
	jle  buton73
	cmp eax, miniB_y + miniB_ysize + 240
	jge buton73
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 60
	mov bl, [esi+1]
	
	make_culoare_macro ebx, area, miniB_x + 51, miniB_y + 240 + 1
	add vector[61], 1
	cmp vector[61], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari72
	cmp ebx, 1
	je inc_succes72
	inc_ratari72:
		inc ratari
		jmp colorare72
	inc_succes72:
		inc succes
		dec parti
		jmp colorare72
	colorare72:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[7][3]
	buton73:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 100
	jle  buton74
	cmp eax, miniB_x + miniB_xsize + 100
	jge buton74
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 240
	jle  buton74
	cmp eax, miniB_y + miniB_ysize + 240
	jge buton74
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 60
	mov bl, [esi+2]
	
	make_culoare_macro ebx, area, miniB_x + 101, miniB_y + 240 + 1
	add vector[62], 1
	cmp vector[62], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari73
	cmp ebx, 1
	je inc_succes73
	inc_ratari73:
		inc ratari
		jmp colorare73
	inc_succes73:
		inc succes
		dec parti
		jmp colorare73
	colorare73:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[7][4]
	buton74:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 150
	jle  buton75
	cmp eax, miniB_x + miniB_xsize + 150
	jge buton75
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 240
	jle  buton75
	cmp eax, miniB_y + miniB_ysize + 240
	jge buton75
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 60
	mov bl, [esi+3]
	
	make_culoare_macro ebx, area, miniB_x + 151, miniB_y + 240 + 1
	add vector[63], 1
	cmp vector[63], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari74
	cmp ebx, 1
	je inc_succes74
	inc_ratari74:
		inc ratari
		jmp colorare74
	inc_succes74:
		inc succes
		dec parti
		jmp colorare74
	colorare74:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[7][5]
	buton75:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 200
	jle  buton76
	cmp eax, miniB_x + miniB_xsize + 200
	jge buton76
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 240
	jle  buton76
	cmp eax, miniB_y + miniB_ysize + 240
	jge buton76
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 60
	mov bl, [esi+4]
	
	make_culoare_macro ebx, area, miniB_x + 201, miniB_y + 240 + 1
	add vector[64], 1
	cmp vector[64], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari75
	cmp ebx, 1
	je inc_succes75
	inc_ratari75:
		inc ratari
		jmp colorare75
	inc_succes75:
		inc succes
		dec parti
		jmp colorare75
	colorare75:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[7][6]
	buton76:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 250
	jle  buton77
	cmp eax, miniB_x + miniB_xsize + 250
	jge buton77
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 240
	jle  buton77
	cmp eax, miniB_y + miniB_ysize + 240
	jge buton77
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 60
	mov bl, [esi+5]
	
	make_culoare_macro ebx, area, miniB_x + 251, miniB_y + 240 + 1
	add vector[65], 1
	cmp vector[65], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari76
	cmp ebx, 1
	je inc_succes76
	inc_ratari76:
		inc ratari
		jmp colorare76
	inc_succes76:
		inc succes
		dec parti
		jmp colorare76
	colorare76:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[7][7]
	buton77:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 300
	jle  buton78
	cmp eax, miniB_x + miniB_xsize + 300
	jge buton78
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 240
	jle  buton78
	cmp eax, miniB_y + miniB_ysize + 240
	jge buton78
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 60
	mov bl, [esi+6]
	
	make_culoare_macro ebx, area, miniB_x + 301, miniB_y + 240 + 1
	add vector[66], 1
	cmp vector[66], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari77
	cmp ebx, 1
	je inc_succes77
	inc_ratari77:
		inc ratari
		jmp colorare77
	inc_succes77:
		inc succes
		dec parti
		jmp colorare77
	colorare77:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[7][8]
	buton78:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 350
	jle  buton79
	cmp eax, miniB_x + miniB_xsize + 350
	jge buton79
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 240
	jle  buton79
	cmp eax, miniB_y + miniB_ysize + 240
	jge buton79
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 60
	mov bl, [esi+7]
	
	make_culoare_macro ebx, area, miniB_x + 351, miniB_y + 240 + 1
	add vector[67], 1
	cmp vector[67], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari78
	cmp ebx, 1
	je inc_succes78
	inc_ratari78:
		inc ratari
		jmp colorare78
	inc_succes78:
		inc succes
		dec parti
		jmp colorare78
	colorare78:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[7][9]
	buton79:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 400
	jle  buton710
	cmp eax, miniB_x + miniB_xsize + 400
	jge buton710
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 240
	jle  buton710
	cmp eax, miniB_y + miniB_ysize + 240
	jge buton710
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 60
	mov bl, [esi+8]
	
	make_culoare_macro ebx, area, miniB_x + 401, miniB_y + 240 + 1
	add vector[68], 1
	cmp vector[68], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari79
	cmp ebx, 1
	je inc_succes79
	inc_ratari79:
		inc ratari
		jmp colorare79
	inc_succes79:
		inc succes
		dec parti
		jmp colorare79
	colorare79:
	jmp afisare_litere
	
	
	;;;;;;;verificare buton[7][10]
	buton710:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 450
	jle  buton81
	cmp eax, miniB_x + miniB_xsize + 450
	jge buton81
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 240
	jle  buton81
	cmp eax, miniB_y + miniB_ysize + 240
	jge buton81
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 60
	mov bl, [esi+9]
	
	make_culoare_macro ebx, area, miniB_x + 451, miniB_y + 240 + 1
	add vector[69], 1
	cmp vector[69], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari710
	cmp ebx, 1
	je inc_succes710
	inc_ratari710:
		inc ratari
		jmp colorare710
	inc_succes710:
		inc succes
		dec parti
		jmp colorare710
	colorare710:
	jmp afisare_litere
	
	
	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;linia8
	
	;;;;;;;;verificare buton[8][1]
	buton81:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x
	jle  buton82
	cmp eax, miniB_x + miniB_xsize
	jge buton82
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 280
	jle  buton82
	cmp eax, miniB_y + miniB_ysize + 280
	jge buton82
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 70
	mov bl, [esi]
	
	make_culoare_macro ebx, area, miniB_x + 1, miniB_y + 280 + 1
	add vector[70], 1
	cmp vector[70], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari81
	cmp ebx, 1
	je inc_succes81
	inc_ratari81:
		inc ratari
		jmp colorare81
	inc_succes81:
		inc succes
		dec parti
		jmp colorare81
	colorare81:
	jmp afisare_litere
	
	
	;;;;;;;;verificare buton[8][2]
	buton82:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 50
	jle  buton83
	cmp eax, miniB_x + miniB_xsize + 50
	jge buton83
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 280
	jle  buton83
	cmp eax, miniB_y + miniB_ysize + 280
	jge buton83
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 70
	mov bl, [esi+1]
	
	make_culoare_macro ebx, area, miniB_x + 51, miniB_y + 280 + 1
	add vector[71], 1
	cmp vector[71], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari82
	cmp ebx, 1
	je inc_succes82
	inc_ratari82:
		inc ratari
		jmp colorare82
	inc_succes82:
		inc succes
		dec parti
		jmp colorare82
	colorare82:
	jmp afisare_litere
	
	
	;;;;;;verificare buton[8][3]
	buton83:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 100
	jle  buton84
	cmp eax, miniB_x + miniB_xsize + 100
	jge buton84
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 280
	jle  buton84
	cmp eax, miniB_y + miniB_ysize + 280
	jge buton84
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 70
	mov bl, [esi+2]
	
	make_culoare_macro ebx, area, miniB_x + 101, miniB_y + 280 + 1
	add vector[72], 1
	cmp vector[72], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari83
	cmp ebx, 1
	je inc_succes83
	inc_ratari83:
		inc ratari
		jmp colorare83
	inc_succes83:
		inc succes
		dec parti
		jmp colorare83
	colorare83:
	jmp afisare_litere
	
	
	;;;;;;verificare buton[8][4]
	buton84:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 150
	jle  buton85
	cmp eax, miniB_x + miniB_xsize + 150
	jge buton85
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 280
	jle  buton85
	cmp eax, miniB_y + miniB_ysize + 280
	jge buton85
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 70
	mov bl, [esi+3]
	
	make_culoare_macro ebx, area, miniB_x + 151, miniB_y + 280 + 1
	add vector[73], 1
	cmp vector[73], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari84
	cmp ebx, 1
	je inc_succes84
	inc_ratari84:
		inc ratari
		jmp colorare84
	inc_succes84:
		inc succes
		dec parti
		jmp colorare84
	colorare84:
	jmp afisare_litere
	
	
	;;;;;;verificare buton[8][5]
	buton85:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 200
	jle  buton86
	cmp eax, miniB_x + miniB_xsize + 200
	jge buton86
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 280
	jle  buton86
	cmp eax, miniB_y + miniB_ysize + 280
	jge buton86
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 70
	mov bl, [esi+4]
	
	make_culoare_macro ebx, area, miniB_x + 201, miniB_y + 280 + 1
	add vector[74], 1
	cmp vector[74], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari85
	cmp ebx, 1
	je inc_succes85
	inc_ratari85:
		inc ratari
		jmp colorare85
	inc_succes85:
		inc succes
		dec parti
		jmp colorare85
	colorare85:
	jmp afisare_litere
	
	
	;;;;;;verificare buton[8][6]
	buton86:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 250
	jle  buton87
	cmp eax, miniB_x + miniB_xsize + 250
	jge buton87
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 280
	jle  buton87
	cmp eax, miniB_y + miniB_ysize + 280
	jge buton87
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 70
	mov bl, [esi+5]
	
	make_culoare_macro ebx, area, miniB_x + 251, miniB_y + 280 + 1
	add vector[75], 1
	cmp vector[75], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari86
	cmp ebx, 1
	je inc_succes86
	inc_ratari86:
		inc ratari
		jmp colorare86
	inc_succes86:
		inc succes
		dec parti
		jmp colorare86
	colorare86:
	jmp afisare_litere
	
	
	;;;;;;verificare buton[8][7]
	buton87:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 300
	jle  buton88
	cmp eax, miniB_x + miniB_xsize + 300
	jge buton88
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 280
	jle  buton88
	cmp eax, miniB_y + miniB_ysize + 280
	jge buton88
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 70
	mov bl, [esi+6]
	
	make_culoare_macro ebx, area, miniB_x + 301, miniB_y + 280 + 1
	add vector[76], 1
	cmp vector[76], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari87
	cmp ebx, 1
	je inc_succes87
	inc_ratari87:
		inc ratari
		jmp colorare87
	inc_succes87:
		inc succes
		dec parti
		jmp colorare87
	colorare87:
	jmp afisare_litere
	
	
	;;;;;;verificare buton[8][8]
	buton88:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 350
	jle  buton89
	cmp eax, miniB_x + miniB_xsize + 350
	jge buton89
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 280
	jle  buton89
	cmp eax, miniB_y + miniB_ysize + 280
	jge buton89
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 70
	mov bl, [esi+7]
	
	make_culoare_macro ebx, area, miniB_x + 351, miniB_y + 280 + 1
	add vector[77], 1
	cmp vector[77], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari88
	cmp ebx, 1
	je inc_succes88
	inc_ratari88:
		inc ratari
		jmp colorare88
	inc_succes88:
		inc succes
		dec parti
		jmp colorare88
	colorare88:
	jmp afisare_litere
	
	
	;;;;;;verificare buton[8][9]
	buton89:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 400
	jle  buton810
	cmp eax, miniB_x + miniB_xsize + 400
	jge buton810
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 280
	jle  buton810
	cmp eax, miniB_y + miniB_ysize + 280
	jge buton810
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 70
	mov bl, [esi+8]
	
	make_culoare_macro ebx, area, miniB_x + 401, miniB_y + 280 + 1
	add vector[78], 1
	cmp vector[78], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari89
	cmp ebx, 1
	je inc_succes89
	inc_ratari89:
		inc ratari
		jmp colorare89
	inc_succes89:
		inc succes
		dec parti
		jmp colorare89
	colorare89:
	jmp afisare_litere
	
	
	;;;;;;verificare buton[8][10]
	buton810:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 450
	jle  buton91
	cmp eax, miniB_x + miniB_xsize + 450
	jge buton91
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 280
	jle  buton91
	cmp eax, miniB_y + miniB_ysize + 280
	jge buton91
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 70
	mov bl, [esi+9]
	
	make_culoare_macro ebx, area, miniB_x + 451, miniB_y + 280 + 1
	add vector[79], 1
	cmp vector[79], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari810
	cmp ebx, 1
	je inc_succes810
	inc_ratari810:
		inc ratari
		jmp colorare810
	inc_succes810:
		inc succes
		dec parti
		jmp colorare810
	colorare810:
	jmp afisare_litere
	
	
	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;linia9
	
	;;;;;;;;verificare buton[9][1]
	buton91:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x
	jle  buton92
	cmp eax, miniB_x + miniB_xsize
	jge buton92
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 320
	jle  buton92
	cmp eax, miniB_y + miniB_ysize + 320
	jge buton92
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 80
	mov bl, [esi]
	
	make_culoare_macro ebx, area, miniB_x + 1, miniB_y + 320 + 1
	add vector[80], 1
	cmp vector[80], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari91
	cmp ebx, 1
	je inc_succes91
	inc_ratari91:
		inc ratari
		jmp colorare91
	inc_succes91:
		inc succes
		dec parti
		jmp colorare91
	colorare91:
	jmp afisare_litere
	
	
	;;;;;;;;verificare buton[9][2]
	buton92:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 50
	jle  buton93
	cmp eax, miniB_x + miniB_xsize + 50
	jge buton93
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 320
	jle  buton93
	cmp eax, miniB_y + miniB_ysize + 320
	jge buton93
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 80
	mov bl, [esi+1]
	
	make_culoare_macro ebx, area, miniB_x + 51, miniB_y + 320 + 1
	add vector[81], 1
	cmp vector[81], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari92
	cmp ebx, 1
	je inc_succes92
	inc_ratari92:
		inc ratari
		jmp colorare92
	inc_succes92:
		inc succes
		dec parti
		jmp colorare92
	colorare92:
	jmp afisare_litere
	
	
	;;;;;;verificare buton[9][3]
	buton93:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 100
	jle  buton94
	cmp eax, miniB_x + miniB_xsize + 100
	jge buton94
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 320
	jle  buton94
	cmp eax, miniB_y + miniB_ysize + 320
	jge buton94
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 80
	mov bl, [esi+2]
	
	make_culoare_macro ebx, area, miniB_x + 101, miniB_y + 320 + 1
	add vector[82], 1
	cmp vector[82], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari93
	cmp ebx, 1
	je inc_succes93
	inc_ratari93:
		inc ratari
		jmp colorare93
	inc_succes93:
		inc succes
		dec parti
		jmp colorare93
	colorare93:
	jmp afisare_litere
	
	
	;;;;;;verificare buton[9][4]
	buton94:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 150
	jle  buton95
	cmp eax, miniB_x + miniB_xsize + 150
	jge buton95
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 320
	jle  buton95
	cmp eax, miniB_y + miniB_ysize + 320
	jge buton95
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 80
	mov bl, [esi+3]
	
	make_culoare_macro ebx, area, miniB_x + 151, miniB_y + 320 + 1
	add vector[83], 1
	cmp vector[83], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari94
	cmp ebx, 1
	je inc_succes94
	inc_ratari94:
		inc ratari
		jmp colorare94
	inc_succes94:
		inc succes
		dec parti
		jmp colorare94
	colorare94:
	jmp afisare_litere
	
	
	;;;;;;verificare buton[9][5]
	buton95:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 200
	jle  buton96
	cmp eax, miniB_x + miniB_xsize + 200
	jge buton96
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 320
	jle  buton96
	cmp eax, miniB_y + miniB_ysize + 320
	jge buton96
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 80
	mov bl, [esi+4]
	
	make_culoare_macro ebx, area, miniB_x + 201, miniB_y + 320 + 1
	add vector[84], 1
	cmp vector[84], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari95
	cmp ebx, 1
	je inc_succes95
	inc_ratari95:
		inc ratari
		jmp colorare95
	inc_succes95:
		inc succes
		dec parti
		jmp colorare95
	colorare95:
	jmp afisare_litere
	
	
	;;;;;;verificare buton[9][6]
	buton96:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 250
	jle  buton97
	cmp eax, miniB_x + miniB_xsize + 250
	jge buton97
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 320
	jle  buton97
	cmp eax, miniB_y + miniB_ysize + 320
	jge buton97
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 80
	mov bl, [esi+5]
	
	make_culoare_macro ebx, area, miniB_x + 251, miniB_y + 320 + 1
	add vector[85], 1
	cmp vector[85], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari96
	cmp ebx, 1
	je inc_succes96
	inc_ratari96:
		inc ratari
		jmp colorare96
	inc_succes96:
		inc succes
		dec parti
		jmp colorare96
	colorare96:
	jmp afisare_litere
	
	
	;;;;;;verificare buton[9][7]
	buton97:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 300
	jle  buton98
	cmp eax, miniB_x + miniB_xsize + 300
	jge buton98
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 320
	jle  buton98
	cmp eax, miniB_y + miniB_ysize + 320
	jge buton98
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 80
	mov bl, [esi+6]
	
	make_culoare_macro ebx, area, miniB_x + 301, miniB_y + 320 + 1
	add vector[86], 1
	cmp vector[86], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari97
	cmp ebx, 1
	je inc_succes97
	inc_ratari97:
		inc ratari
		jmp colorare97
	inc_succes97:
		inc succes
		dec parti
		jmp colorare97
	colorare97:
	jmp afisare_litere
	
	
	;;;;;;verificare buton[9][8]
	buton98:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 350
	jle  buton99
	cmp eax, miniB_x + miniB_xsize + 350
	jge buton99
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 320
	jle  buton99
	cmp eax, miniB_y + miniB_ysize + 320
	jge buton99
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 80
	mov bl, [esi+7]
	
	make_culoare_macro ebx, area, miniB_x + 351, miniB_y + 320 + 1
	add vector[87], 1
	cmp vector[87], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari98
	cmp ebx, 1
	je inc_succes98
	inc_ratari98:
		inc ratari
		jmp colorare98
	inc_succes98:
		inc succes
		dec parti
		jmp colorare98
	colorare98:
	jmp afisare_litere
	
	
	;;;;;;verificare buton[9][9]
	buton99:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 400
	jle  buton910
	cmp eax, miniB_x + miniB_xsize + 400
	jge buton910
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 320
	jle  buton910
	cmp eax, miniB_y + miniB_ysize + 320
	jge buton910
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 80
	mov bl, [esi+8]
	
	make_culoare_macro ebx, area, miniB_x + 401, miniB_y + 320 + 1
	add vector[88], 1
	cmp vector[88], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari99
	cmp ebx, 1
	je inc_succes99
	inc_ratari99:
		inc ratari
		jmp colorare99
	inc_succes99:
		inc succes
		dec parti
		jmp colorare99
	colorare99:
	jmp afisare_litere
	
	
	;;;;;;verificare buton[9][10]
	buton910:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 450
	jle  buton101
	cmp eax, miniB_x + miniB_xsize + 450
	jge buton101
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 320
	jle  buton101
	cmp eax, miniB_y + miniB_ysize + 320
	jge buton101
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 80
	mov bl, [esi+9]
	
	make_culoare_macro ebx, area, miniB_x + 451, miniB_y + 320 + 1
	add vector[89], 1
	cmp vector[89], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari910
	cmp ebx, 1
	je inc_succes910
	inc_ratari910:
		inc ratari
		jmp colorare910
	inc_succes910:
		inc succes
		dec parti
		jmp colorare910
	colorare910:
	jmp afisare_litere
	
	
	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;linia10
	
	;;;;;;;;verificare buton[10][1]
	buton101:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x
	jle  buton102
	cmp eax, miniB_x + miniB_xsize
	jge buton102
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 360
	jle  buton102
	cmp eax, miniB_y + miniB_ysize + 360
	jge buton102

	
	lea esi, Table_game
	mov ebx, 0
	add esi, 90
	mov bl, [esi]
	
	make_culoare_macro ebx, area, miniB_x + 1, miniB_y + 360 + 1
	add vector[90], 1
	cmp vector[90], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari101
	cmp ebx, 1
	je inc_succes101
	inc_ratari101:
		inc ratari
		jmp colorare101
	inc_succes101:
		inc succes
		dec parti
		jmp colorare101
	colorare101:
	jmp afisare_litere
	
	
	;;;;;;;;verificare buton[10][2]
	buton102:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 50
	jle  buton103
	cmp eax, miniB_x + miniB_xsize + 50
	jge buton103
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 360
	jle  buton103
	cmp eax, miniB_y + miniB_ysize + 360
	jge buton103
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 90
	mov bl, [esi+1]
	
	make_culoare_macro ebx, area, miniB_x + 51, miniB_y + 360 + 1
	add vector[91], 1
	cmp vector[91], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari102
	cmp ebx, 1
	je inc_succes102
	inc_ratari102:
		inc ratari
		jmp colorare102
	inc_succes102:
		inc succes
		dec parti
		jmp colorare102
	colorare102:
	jmp afisare_litere
	
	
	;;;;;;verificare buton[10][3]
	buton103:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 100
	jle  buton104
	cmp eax, miniB_x + miniB_xsize + 100
	jge buton104
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 360
	jle  buton104
	cmp eax, miniB_y + miniB_ysize + 360
	jge buton104
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 90
	mov bl, [esi+2]
	
	make_culoare_macro ebx, area, miniB_x + 101, miniB_y + 360 + 1
	add vector[92], 1
	cmp vector[92], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari103
	cmp ebx, 1
	je inc_succes103
	inc_ratari103:
		inc ratari
		jmp colorare103
	inc_succes103:
		inc succes
		dec parti
		jmp colorare103
	colorare103:
	jmp afisare_litere
	
	
	;;;;;;verificare buton[10][4]
	buton104:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 150
	jle  buton105
	cmp eax, miniB_x + miniB_xsize + 150
	jge buton105
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 360
	jle  buton105
	cmp eax, miniB_y + miniB_ysize + 360
	jge buton105
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 90
	mov bl, [esi+3]
	
	make_culoare_macro ebx, area, miniB_x + 151, miniB_y + 360 + 1
	add vector[93], 1
	cmp vector[93], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari104
	cmp ebx, 1
	je inc_succes104
	inc_ratari104:
		inc ratari
		jmp colorare104
	inc_succes104:
		inc succes
		dec parti
		jmp colorare104
	colorare104:
	jmp afisare_litere
	
	
	;;;;;;verificare buton[10][5]
	buton105:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 200
	jle  buton106
	cmp eax, miniB_x + miniB_xsize + 200
	jge buton106
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 360
	jle  buton106
	cmp eax, miniB_y + miniB_ysize + 360
	jge buton106
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 90
	mov bl, [esi+4]
	
	make_culoare_macro ebx, area, miniB_x + 201, miniB_y + 360 + 1
	add vector[94], 1
	cmp vector[94], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari105
	cmp ebx, 1
	je inc_succes105
	inc_ratari105:
		inc ratari
		jmp colorare105
	inc_succes105:
		inc succes
		dec parti
		jmp colorare105
	colorare105:
	jmp afisare_litere
	
	
	;;;;;;verificare buton[10][6]
	buton106:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 250
	jle  buton107
	cmp eax, miniB_x + miniB_xsize + 250
	jge buton107
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 360
	jle  buton107
	cmp eax, miniB_y + miniB_ysize + 360
	jge buton107
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 90
	mov bl, [esi+5]
	
	make_culoare_macro ebx, area, miniB_x + 251, miniB_y + 360 + 1
	add vector[95], 1
	cmp vector[95], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari106
	cmp ebx, 1
	je inc_succes106
	inc_ratari106:
		inc ratari
		jmp colorare106
	inc_succes106:
		inc succes
		dec parti
		jmp colorare106
	colorare106:
	jmp afisare_litere
	
	
	;;;;;;verificare buton[10][7]
	buton107:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 300
	jle  buton108
	cmp eax, miniB_x + miniB_xsize + 300
	jge buton108
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 360
	jle  buton108
	cmp eax, miniB_y + miniB_ysize + 360
	jge buton108
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 90
	mov bl, [esi+6]
	
	make_culoare_macro ebx, area, miniB_x + 301, miniB_y + 360 + 1
	add vector[96], 1
	cmp vector[96], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari107
	cmp ebx, 1
	je inc_succes107
	inc_ratari107:
		inc ratari
		jmp colorare107
	inc_succes107:
		inc succes
		dec parti
		jmp colorare107
	colorare107:
	jmp afisare_litere
	
	
	;;;;;;verificare buton[10][8]
	buton108:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 350
	jle  buton109
	cmp eax, miniB_x + miniB_xsize + 350
	jge buton109
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 360
	jle  buton109
	cmp eax, miniB_y + miniB_ysize + 360
	jge buton109
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 90
	mov bl, [esi+7]
	
	make_culoare_macro ebx, area, miniB_x + 351, miniB_y + 360 + 1
	add vector[97], 1
	cmp vector[97], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari108
	cmp ebx, 1
	je inc_succes108
	inc_ratari108:
		inc ratari
		jmp colorare108
	inc_succes108:
		inc succes
		dec parti
		jmp colorare108
	colorare108:
	jmp afisare_litere
	
	
	;;;;;;verificare buton[10][9]
	buton109:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 400
	jle  buton1010
	cmp eax, miniB_x + miniB_xsize + 400
	jge buton1010
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 360
	jle  buton1010
	cmp eax, miniB_y + miniB_ysize + 360
	jge buton1010
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 90
	mov bl, [esi+8]
	
	make_culoare_macro ebx, area, miniB_x + 401, miniB_y + 360 + 1
	add vector[98], 1
	cmp vector[98], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari109
	cmp ebx, 1
	je inc_succes109
	inc_ratari109:
		inc ratari
		jmp colorare109
	inc_succes109:
		inc succes
		dec parti
		jmp colorare109
	colorare109:
	jmp afisare_litere
	
	
	;;;;;;verificare buton[10][10]
	buton1010:
	mov eax, [ebp+arg2]
	cmp eax, miniB_x + 450
	jle  button_fail
	cmp eax, miniB_x + miniB_xsize + 450
	jge button_fail
	
	mov eax, [ebp+arg3]
	cmp eax, miniB_y + 360
	jle  button_fail
	cmp eax, miniB_y + miniB_ysize + 360
	jge button_fail
	
	lea esi, Table_game
	mov ebx, 0
	add esi, 90
	mov bl, [esi+9]
	
	make_culoare_macro ebx, area, miniB_x + 451, miniB_y + 360 + 1
	add vector[99], 1
	cmp vector[99], 1
	ja afisare_litere
	
	cmp ebx, 0
	je inc_ratari1010
	cmp ebx, 1
	je inc_succes1010
	inc_ratari1010:
		inc ratari
		jmp colorare1010
	inc_succes1010:
		inc succes
		dec parti
		jmp colorare1010
	colorare1010:
	jmp afisare_litere
	

	
	; s-a dat click in buton
	
	;culorare_buton [ebp+arg2],[ebp+arg3], button_size, 0FF0000h
	
button_fail:
	; make_text_macro ' ', area, button_x + button_size/3 - 5, button_y + button_size + 10
	; make_text_macro ' ', area, button_x + button_size/3 + 5, button_y + button_size + 10
	make_text_macro ' ', area, button_hx + button_hsize/2 -40, button_hy + button_hsize + 10
	make_text_macro ' ', area, button_hx + button_hsize/2 -30, button_hy + button_hsize + 10
	make_text_macro ' ', area, button_hx + button_hsize/2 -20, button_hy + button_hsize + 10
	make_text_macro ' ', area, button_hx + button_hsize/2 -10, button_hy + button_hsize + 10
	make_text_macro ' ', area, button_hx + button_hsize/2, button_hy + button_hsize + 10
	make_text_macro ' ', area, button_hx + button_hsize/2 +10, button_hy + button_hsize + 10
	make_text_macro ' ', area, button_hx + button_hsize/2 +20, button_hy + button_hsize + 10
	make_text_macro ' ', area, button_hx + button_hsize/2 +30, button_hy + button_hsize + 10
	
	line_horizontal harta_x, harta_y, harta_xsize, 0FFFFFFh
	line_horizontal harta_x, harta_y + harta_ysize, harta_xsize, 0FFFFFFh
	line_vertical harta_x, harta_y, harta_ysize, 0FFFFFFh
	line_vertical harta_x + harta_xsize, harta_y, harta_ysize, 0FFFFFFh
	
	make_text_macro ' ', area, harta_x + harta_xsize/2 -20 , harta_y -20 
	make_text_macro ' ', area, harta_x + harta_xsize/2 -10, harta_y -20
	make_text_macro ' ', area, harta_x + harta_xsize/2, harta_y -20
	make_text_macro ' ', area, harta_x + harta_xsize/2 +10, harta_y - 20
	make_text_macro ' ', area, harta_x + harta_xsize/2 +20, harta_y - 20
	
	mov y, 101
	mov ebx, 0
	for11:
		push ebx
		mov ecx, 0
		mov x, 951
		for22:
			push ecx
			make_culoare_macro 2, area, x, y
			pop ecx
			inc ecx
			add x, 50
			cmp ecx, 10
		jb for22
		pop ebx
		inc ebx
		add y, 40
		cmp ebx, 10
	jb for11
	
	jmp afisare_litere          ;raman si se afiseze proiect... pt orice event
	
evt_timer:
	inc counter
	inc counterOK
	inc counterHarta
	cmp counterHarta, 15
	je button_fail
	
afisare_litere:
	;afisam valoarea counter-ului curent (sute, zeci si unitati)
	mov ebx, 10
	mov eax, counter
	;cifra unitatilor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 30, 10
	;cifra zecilor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 20, 10
	;cifra sutelor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 10, 10
	
	;scriem un mesaj
	make_text_macro 'P', area, 550, 30
	make_text_macro 'R', area, 560, 30
	make_text_macro 'O', area, 570, 30
	make_text_macro 'I', area, 580, 30
	make_text_macro 'E', area, 590, 30
	make_text_macro 'C', area, 600, 30
	make_text_macro 'T', area, 610, 30
	
	make_text_macro 'L', area, 630, 30
	make_text_macro 'A', area, 640, 30
	
	make_text_macro 'A', area, 660, 30
	make_text_macro 'S', area, 670, 30
	make_text_macro 'A', area, 680, 30
	make_text_macro 'M', area, 690, 30
	make_text_macro 'B', area, 700, 30
	make_text_macro 'L', area, 710, 30
	make_text_macro 'A', area, 720, 30
	make_text_macro 'R', area, 730, 30
	make_text_macro 'E', area, 740, 30
	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
	make_text_macro 'J', area, 570, 50
	make_text_macro 'O', area, 580, 50
	make_text_macro 'C', area, 590, 50
	make_text_macro 'U', area, 600, 50
	make_text_macro 'L', area, 610, 50
	
	make_text_macro 'C', area, 630, 50
	make_text_macro 'U', area, 640, 50
	
	make_text_macro 'V', area, 660, 50
	make_text_macro 'A', area, 670, 50
	make_text_macro 'P', area, 680, 50
	make_text_macro 'O', area, 690, 50
	make_text_macro 'R', area, 700, 50
	make_text_macro 'A', area, 710, 50
	make_text_macro 'S', area, 720, 50
	make_text_macro 'E', area, 730, 50
	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
	make_text_macro 'A', area, 1300, 610
	make_text_macro 'N', area, 1310, 610
	make_text_macro 'U', area, 1320, 610
	make_text_macro 'L', area, 1330, 610
	make_text_macro ':', area, 1340, 610
	make_text_macro '2', area, 1350, 610
	make_text_macro '0', area, 1360, 610
	make_text_macro '2', area, 1370, 610
	make_text_macro '2', area, 1380, 610
	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
	make_text_macro 'B', area, 1300, 630
	make_text_macro 'U', area, 1310, 630
	make_text_macro 'D', area, 1320, 630
	make_text_macro 'A', area, 1330, 630
	
	make_text_macro 'A', area, 1350, 630
	make_text_macro 'N', area, 1360, 630
	make_text_macro 'D', area, 1370, 630
	make_text_macro 'R', area, 1380, 630
	make_text_macro 'E', area, 1390, 630
	make_text_macro 'E', area, 1400, 630
	make_text_macro 'A', area, 1410, 630
	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
	make_text_macro 'G', area, 1300, 650
	make_text_macro 'R', area, 1310, 650
	make_text_macro 'U', area, 1320, 650
	make_text_macro 'P', area, 1330, 650
	make_text_macro 'A', area, 1340, 650
	
	make_text_macro '3', area, 1360, 650
	make_text_macro '0', area, 1370, 650
	make_text_macro '2', area, 1380, 650
	make_text_macro '1', area, 1390, 650
	make_text_macro '1', area, 1400, 650
	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
	make_text_macro 'B', area, 120, 530
	make_text_macro 'U', area, 130, 530
	make_text_macro 'T', area, 140, 530
	make_text_macro 'O', area, 150, 530
	make_text_macro 'N', area, 160, 530

	make_text_macro 'H', area, 120, 550
	make_text_macro 'A', area, 130, 550
	make_text_macro 'R', area, 140, 550
	make_text_macro 'T', area, 150, 550
	make_text_macro 'A', area, 160, 550
	
	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
	
	make_text_macro 'P', area, 20, 250
	make_text_macro 'A', area, 30, 250
	make_text_macro 'R', area, 40, 250
	make_text_macro 'T', area, 50, 250
	make_text_macro 'I', area, 60, 250

	make_text_macro 'N', area, 80, 250
	make_text_macro 'E', area, 90, 250
	make_text_macro 'D', area, 100, 250
	make_text_macro 'E', area, 110, 250
	make_text_macro 'S', area, 120, 250
	make_text_macro 'C', area, 130, 250
	make_text_macro 'O', area, 140, 250
	make_text_macro 'P', area, 150, 250
	make_text_macro 'E', area, 160, 250
	make_text_macro 'R', area, 170, 250
	make_text_macro 'I', area, 180, 250
	make_text_macro 'T', area, 190, 250
	make_text_macro 'E', area, 200, 250
	
	make_text_macro 'I', area, 220, 250
	make_text_macro 'N', area, 230, 250
	make_text_macro 'C', area, 240, 250
	make_text_macro 'A', area, 250, 250
	make_text_macro ':', area, 260, 250
	
	;afisam bucati ramase de descoperit
	mov ebx, 10
	mov eax, parti
	;cifra unitatilor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 290, 250
	;cifra zecilor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 280, 250
	;cifra sutelor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 270, 250
	
	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
	make_text_macro 'L', area, 20, 290
	make_text_macro 'O', area, 30, 290
	make_text_macro 'V', area, 40, 290
	make_text_macro 'I', area, 50, 290
	make_text_macro 'T', area, 60, 290
	make_text_macro 'U', area, 70, 290
	make_text_macro 'R', area, 80, 290
	make_text_macro 'I', area, 90, 290
	
	make_text_macro 'C', area, 110, 290
	make_text_macro 'U', area, 120, 290
	
	make_text_macro 'S', area, 140, 290
	make_text_macro 'U', area, 150, 290
	make_text_macro 'C', area, 160, 290
	make_text_macro 'C', area, 170, 290
	make_text_macro 'E', area, 180, 290
	make_text_macro 'S', area, 190, 290
	make_text_macro ':', area, 200, 290
	
	
	;afisam lovituri cu succes
	mov ebx, 10
	mov eax, succes
	;cifra unitatilor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 230, 290
	;cifra zecilor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 220, 290
	;cifra sutelor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 210, 290
	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
	make_text_macro 'R', area, 20, 330
	make_text_macro 'A', area, 30, 330
	make_text_macro 'T', area, 40, 330
	make_text_macro 'A', area, 50, 330
	make_text_macro 'R', area, 60, 330
	make_text_macro 'I', area, 70, 330
	make_text_macro ':', area, 80, 330
	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
	
	;afisam ratari
	mov ebx, 10
	mov eax, ratari
	;cifra unitatilor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 110, 330
	;cifra zecilor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 100, 330
	;cifra sutelor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 90, 330
	
	cmp parti, 0
	jne necastig
	castig:
	
	make_text_macro 'A', area, 600, 600
	make_text_macro 'I', area, 610, 600
	
	make_text_macro 'C', area, 630, 600
	make_text_macro 'A', area, 640, 600
	make_text_macro 'S', area, 650, 600
	make_text_macro 'T', area, 660, 600
	make_text_macro 'I', area, 670, 600
	make_text_macro 'G', area, 680, 600
	make_text_macro 'A', area, 690, 600
	make_text_macro 'T', area, 700, 600
	make_text_macro '!', area, 710, 600
	
	necastig: 
	
	;;;;;;asa afisam un patrat pe ecran
	;;;;;;tabelul pentru joc
	line_horizontal button_x, button_y, button_xsize, 0
	line_horizontal button_x, button_y + button_ysize, button_xsize, 0
	line_vertical button_x, button_y, button_ysize, 0
	line_vertical button_x + button_xsize, button_y, button_ysize, 0
	;;;;;;;;;;;buton harta
	line_horizontal button_hx, button_hy, button_hsize, 0
	line_horizontal button_hx , button_hy + button_hsize , button_hsize, 0
	line_vertical button_hx, button_hy, button_hsize, 0
	line_vertical button_hx + button_hsize, button_hy, button_hsize, 0
	
	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
	;linia 1
	;//////////////////////////////////////////////////////////
	line_horizontal miniB_x, miniB_y, miniB_xsize, 0
	line_horizontal miniB_x, miniB_y + miniB_ysize - 1, miniB_xsize, 0
	line_vertical miniB_x, miniB_y, miniB_ysize, 0
	line_vertical miniB_x + miniB_xsize - 1, miniB_y, miniB_ysize, 0
	
	line_horizontal miniB_x+50, miniB_y, miniB_xsize, 0
	line_horizontal miniB_x+50, miniB_y + miniB_ysize - 1, miniB_xsize, 0
	line_vertical miniB_x+50, miniB_y, miniB_ysize, 0
	line_vertical miniB_x+50 + miniB_xsize - 1, miniB_y, miniB_ysize, 0
	
	line_horizontal miniB_x+100, miniB_y, miniB_xsize, 0
	line_horizontal miniB_x+100, miniB_y + miniB_ysize - 1, miniB_xsize, 0
	line_vertical miniB_x+100, miniB_y, miniB_ysize, 0
	line_vertical miniB_x+100 + miniB_xsize - 1, miniB_y, miniB_ysize, 0
	
	line_horizontal miniB_x+150, miniB_y, miniB_xsize, 0
	line_horizontal miniB_x+150, miniB_y + miniB_ysize - 1, miniB_xsize, 0
	line_vertical miniB_x+150, miniB_y, miniB_ysize, 0
	line_vertical miniB_x+150 + miniB_xsize - 1, miniB_y, miniB_ysize, 0
	
	line_horizontal miniB_x+200, miniB_y, miniB_xsize, 0
	line_horizontal miniB_x+200, miniB_y + miniB_ysize - 1, miniB_xsize, 0
	line_vertical miniB_x+200, miniB_y, miniB_ysize, 0
	line_vertical miniB_x+200 + miniB_xsize - 1, miniB_y, miniB_ysize, 0
	
	line_horizontal miniB_x+250, miniB_y, miniB_xsize, 0
	line_horizontal miniB_x+250, miniB_y + miniB_ysize - 1, miniB_xsize, 0
	line_vertical miniB_x+250, miniB_y, miniB_ysize, 0
	line_vertical miniB_x+250 + miniB_xsize - 1, miniB_y, miniB_ysize, 0
	
	line_horizontal miniB_x+300, miniB_y, miniB_xsize, 0
	line_horizontal miniB_x+300, miniB_y + miniB_ysize - 1, miniB_xsize, 0
	line_vertical miniB_x+300, miniB_y, miniB_ysize, 0
	line_vertical miniB_x+300 + miniB_xsize - 1, miniB_y, miniB_ysize, 0
	
	line_horizontal miniB_x+350, miniB_y, miniB_xsize, 0
	line_horizontal miniB_x+350, miniB_y + miniB_ysize - 1, miniB_xsize, 0
	line_vertical miniB_x+350, miniB_y, miniB_ysize, 0
	line_vertical miniB_x+350 + miniB_xsize - 1, miniB_y, miniB_ysize, 0
	
	line_horizontal miniB_x+400, miniB_y, miniB_xsize, 0
	line_horizontal miniB_x+400, miniB_y + miniB_ysize - 1, miniB_xsize, 0
	line_vertical miniB_x+400, miniB_y, miniB_ysize, 0
	line_vertical miniB_x+400 + miniB_xsize - 1, miniB_y, miniB_ysize, 0
	
	line_horizontal miniB_x+450, miniB_y, miniB_xsize, 0
	line_horizontal miniB_x+450, miniB_y + miniB_ysize - 1, miniB_xsize, 0
	line_vertical miniB_x+450, miniB_y, miniB_ysize, 0
	line_vertical miniB_x+450 + miniB_xsize - 1, miniB_y, miniB_ysize, 0
	
	;////////////////////////////////////////////////////////////////
	;linia2
	;////////////////////////////////////////////////////////////////
	line_horizontal miniB_x, miniB_y+40, miniB_xsize, 0
	line_horizontal miniB_x, miniB_y +40+ miniB_ysize - 1, miniB_xsize, 0
	line_vertical miniB_x, miniB_y+40, miniB_ysize, 0
	line_vertical miniB_x + miniB_xsize - 1, miniB_y+40, miniB_ysize, 0
	
	line_horizontal miniB_x+50, miniB_y+40, miniB_xsize, 0
	line_horizontal miniB_x+50, miniB_y +40+ miniB_ysize - 1, miniB_xsize, 0
	line_vertical miniB_x+50, miniB_y+40, miniB_ysize, 0
	line_vertical miniB_x+50 + miniB_xsize - 1, miniB_y+40, miniB_ysize, 0
	
	line_horizontal miniB_x+100, miniB_y+40, miniB_xsize, 0
	line_horizontal miniB_x+100, miniB_y + miniB_ysize+40 - 1, miniB_xsize, 0
	line_vertical miniB_x+100, miniB_y+40, miniB_ysize, 0
	line_vertical miniB_x+100 + miniB_xsize - 1, miniB_y+40, miniB_ysize, 0
	
	line_horizontal miniB_x+150, miniB_y+40, miniB_xsize, 0
	line_horizontal miniB_x+150, miniB_y + miniB_ysize+40 - 1, miniB_xsize, 0
	line_vertical miniB_x+150, miniB_y+40, miniB_ysize, 0
	line_vertical miniB_x+150 + miniB_xsize - 1, miniB_y+40, miniB_ysize, 0
	
	line_horizontal miniB_x+200, miniB_y+40, miniB_xsize, 0
	line_horizontal miniB_x+200, miniB_y + miniB_ysize+40 - 1, miniB_xsize, 0
	line_vertical miniB_x+200, miniB_y+40, miniB_ysize, 0
	line_vertical miniB_x+200 + miniB_xsize - 1, miniB_y+40, miniB_ysize, 0
	
	line_horizontal miniB_x+250, miniB_y+40, miniB_xsize, 0
	line_horizontal miniB_x+250, miniB_y + miniB_ysize+40 - 1, miniB_xsize, 0
	line_vertical miniB_x+250, miniB_y+40, miniB_ysize, 0
	line_vertical miniB_x+250 + miniB_xsize - 1, miniB_y+40, miniB_ysize, 0
	
	line_horizontal miniB_x+300, miniB_y+40, miniB_xsize, 0
	line_horizontal miniB_x+300, miniB_y + miniB_ysize+40 - 1, miniB_xsize, 0
	line_vertical miniB_x+300, miniB_y+40, miniB_ysize, 0
	line_vertical miniB_x+300 + miniB_xsize - 1, miniB_y+40, miniB_ysize, 0
	
	line_horizontal miniB_x+350, miniB_y+40, miniB_xsize, 0
	line_horizontal miniB_x+350, miniB_y + miniB_ysize+40 - 1, miniB_xsize, 0
	line_vertical miniB_x+350, miniB_y+40, miniB_ysize, 0
	line_vertical miniB_x+350 + miniB_xsize - 1, miniB_y+40, miniB_ysize, 0
	
	line_horizontal miniB_x+400, miniB_y+40, miniB_xsize, 0
	line_horizontal miniB_x+400, miniB_y + miniB_ysize+40 - 1, miniB_xsize, 0
	line_vertical miniB_x+400, miniB_y+40, miniB_ysize, 0
	line_vertical miniB_x+400 + miniB_xsize - 1, miniB_y+40, miniB_ysize, 0
	
	line_horizontal miniB_x+450, miniB_y+40, miniB_xsize, 0
	line_horizontal miniB_x+450, miniB_y + miniB_ysize+40 - 1, miniB_xsize, 0
	line_vertical miniB_x+450, miniB_y+40, miniB_ysize, 0
	line_vertical miniB_x+450 + miniB_xsize - 1, miniB_y+40, miniB_ysize, 0
	
	;////////////////////////////////////////////////////////////////
	;linia3
	;////////////////////////////////////////////////////////////////
	line_horizontal miniB_x, miniB_y+80, miniB_xsize, 0
	line_horizontal miniB_x, miniB_y +80+ miniB_ysize - 1, miniB_xsize, 0
	line_vertical miniB_x, miniB_y+80, miniB_ysize, 0
	line_vertical miniB_x + miniB_xsize - 1, miniB_y+80, miniB_ysize, 0
	
	line_horizontal miniB_x+50, miniB_y+80, miniB_xsize, 0
	line_horizontal miniB_x+50, miniB_y +80+ miniB_ysize - 1, miniB_xsize, 0
	line_vertical miniB_x+50, miniB_y+80, miniB_ysize, 0
	line_vertical miniB_x+50 + miniB_xsize - 1, miniB_y+80, miniB_ysize, 0
	
	line_horizontal miniB_x+100, miniB_y+80, miniB_xsize, 0
	line_horizontal miniB_x+100, miniB_y + miniB_ysize+80 - 1, miniB_xsize, 0
	line_vertical miniB_x+100, miniB_y+80, miniB_ysize, 0
	line_vertical miniB_x+100 + miniB_xsize -1 , miniB_y+80, miniB_ysize, 0
	
	line_horizontal miniB_x+150, miniB_y+80, miniB_xsize, 0
	line_horizontal miniB_x+150, miniB_y + miniB_ysize+80 - 1, miniB_xsize, 0
	line_vertical miniB_x+150, miniB_y+80, miniB_ysize, 0
	line_vertical miniB_x+150 + miniB_xsize - 1, miniB_y+80, miniB_ysize, 0
	
	line_horizontal miniB_x+200, miniB_y+80, miniB_xsize, 0
	line_horizontal miniB_x+200, miniB_y + miniB_ysize+80 - 1, miniB_xsize, 0
	line_vertical miniB_x+200, miniB_y+80, miniB_ysize, 0
	line_vertical miniB_x+200 + miniB_xsize - 1, miniB_y+80, miniB_ysize, 0
	
	line_horizontal miniB_x+250, miniB_y+80, miniB_xsize, 0
	line_horizontal miniB_x+250, miniB_y + miniB_ysize+80 - 1, miniB_xsize, 0
	line_vertical miniB_x+250, miniB_y+80, miniB_ysize, 0
	line_vertical miniB_x+250 + miniB_xsize - 1, miniB_y+80, miniB_ysize, 0
	
	line_horizontal miniB_x+300, miniB_y+80, miniB_xsize, 0
	line_horizontal miniB_x+300, miniB_y + miniB_ysize+80 - 1, miniB_xsize, 0
	line_vertical miniB_x+300, miniB_y+80, miniB_ysize, 0
	line_vertical miniB_x+300 + miniB_xsize - 1, miniB_y+80, miniB_ysize, 0
	
	line_horizontal miniB_x+350, miniB_y+80, miniB_xsize, 0
	line_horizontal miniB_x+350, miniB_y + miniB_ysize+80 -1, miniB_xsize, 0
	line_vertical miniB_x+350, miniB_y+80, miniB_ysize, 0
	line_vertical miniB_x+350 + miniB_xsize - 1, miniB_y+80, miniB_ysize, 0
	
	line_horizontal miniB_x+400, miniB_y+80, miniB_xsize, 0
	line_horizontal miniB_x+400, miniB_y + miniB_ysize+80 - 1, miniB_xsize, 0
	line_vertical miniB_x+400, miniB_y+80, miniB_ysize, 0
	line_vertical miniB_x+400 + miniB_xsize - 1, miniB_y+80, miniB_ysize, 0
	
	line_horizontal miniB_x+450, miniB_y+80, miniB_xsize, 0
	line_horizontal miniB_x+450, miniB_y + miniB_ysize+80 - 1, miniB_xsize, 0
	line_vertical miniB_x+450, miniB_y+80, miniB_ysize, 0
	line_vertical miniB_x+450 + miniB_xsize - 1, miniB_y+80, miniB_ysize, 0
	;////////////////////////////////////////////////////////////////
	;linia4
	;////////////////////////////////////////////////////////////////
	line_horizontal miniB_x, miniB_y+120, miniB_xsize, 0
	line_horizontal miniB_x, miniB_y +120+ miniB_ysize - 1, miniB_xsize, 0
	line_vertical miniB_x, miniB_y+120, miniB_ysize, 0
	line_vertical miniB_x + miniB_xsize - 1, miniB_y+120, miniB_ysize, 0
	
	line_horizontal miniB_x+50, miniB_y+120, miniB_xsize, 0
	line_horizontal miniB_x+50, miniB_y +120+ miniB_ysize - 1, miniB_xsize, 0
	line_vertical miniB_x+50, miniB_y+120, miniB_ysize, 0
	line_vertical miniB_x+50 + miniB_xsize - 1, miniB_y+120, miniB_ysize, 0
	
	line_horizontal miniB_x+100, miniB_y+120, miniB_xsize, 0
	line_horizontal miniB_x+100, miniB_y + miniB_ysize+120 - 1, miniB_xsize, 0
	line_vertical miniB_x+100, miniB_y+120, miniB_ysize, 0
	line_vertical miniB_x+100 + miniB_xsize - 1, miniB_y+120, miniB_ysize, 0
	
	line_horizontal miniB_x+150, miniB_y+120, miniB_xsize, 0
	line_horizontal miniB_x+150, miniB_y + miniB_ysize+120 - 1, miniB_xsize, 0
	line_vertical miniB_x+150, miniB_y+120, miniB_ysize, 0
	line_vertical miniB_x+150 + miniB_xsize - 1, miniB_y+120, miniB_ysize, 0
	
	line_horizontal miniB_x+200, miniB_y+120, miniB_xsize, 0
	line_horizontal miniB_x+200, miniB_y + miniB_ysize+120 - 1, miniB_xsize, 0
	line_vertical miniB_x+200, miniB_y+120, miniB_ysize, 0
	line_vertical miniB_x+200 + miniB_xsize - 1, miniB_y+120, miniB_ysize, 0
	
	line_horizontal miniB_x+250, miniB_y+120, miniB_xsize, 0
	line_horizontal miniB_x+250, miniB_y + miniB_ysize+120 - 1, miniB_xsize, 0
	line_vertical miniB_x+250, miniB_y+120, miniB_ysize, 0
	line_vertical miniB_x+250 + miniB_xsize - 1, miniB_y+120, miniB_ysize, 0
	
	line_horizontal miniB_x+300, miniB_y+120, miniB_xsize, 0
	line_horizontal miniB_x+300, miniB_y + miniB_ysize+120 - 1, miniB_xsize, 0
	line_vertical miniB_x+300, miniB_y+120, miniB_ysize, 0
	line_vertical miniB_x+300 + miniB_xsize - 1, miniB_y+120, miniB_ysize, 0
	
	line_horizontal miniB_x+350, miniB_y+120, miniB_xsize, 0
	line_horizontal miniB_x+350, miniB_y + miniB_ysize+120 - 1, miniB_xsize, 0
	line_vertical miniB_x+350, miniB_y+120, miniB_ysize, 0
	line_vertical miniB_x+350 + miniB_xsize - 1, miniB_y+120, miniB_ysize, 0
	
	line_horizontal miniB_x+400, miniB_y+120, miniB_xsize, 0
	line_horizontal miniB_x+400, miniB_y + miniB_ysize+120 - 1, miniB_xsize, 0
	line_vertical miniB_x+400, miniB_y+120, miniB_ysize, 0
	line_vertical miniB_x+400 + miniB_xsize - 1, miniB_y+120, miniB_ysize, 0
	
	line_horizontal miniB_x+450, miniB_y+120, miniB_xsize, 0
	line_horizontal miniB_x+450, miniB_y + miniB_ysize+120 - 1, miniB_xsize, 0
	line_vertical miniB_x+450, miniB_y+120, miniB_ysize, 0
	line_vertical miniB_x+450 + miniB_xsize - 1, miniB_y+120, miniB_ysize, 0
	;////////////////////////////////////////////////////////////////
	;linia5
	;////////////////////////////////////////////////////////////////
	line_horizontal miniB_x, miniB_y+160, miniB_xsize, 0
	line_horizontal miniB_x, miniB_y +160+ miniB_ysize - 1, miniB_xsize, 0
	line_vertical miniB_x, miniB_y+160, miniB_ysize, 0
	line_vertical miniB_x + miniB_xsize - 1, miniB_y+160, miniB_ysize, 0
	
	line_horizontal miniB_x+50, miniB_y+160, miniB_xsize, 0
	line_horizontal miniB_x+50, miniB_y +160+ miniB_ysize - 1, miniB_xsize, 0
	line_vertical miniB_x+50, miniB_y+160, miniB_ysize, 0
	line_vertical miniB_x+50 + miniB_xsize - 1, miniB_y+160, miniB_ysize, 0
	
	line_horizontal miniB_x+100, miniB_y+160, miniB_xsize, 0
	line_horizontal miniB_x+100, miniB_y + miniB_ysize+160 - 1, miniB_xsize, 0
	line_vertical miniB_x+100, miniB_y+160, miniB_ysize, 0
	line_vertical miniB_x+100 + miniB_xsize - 1, miniB_y+160, miniB_ysize, 0
	
	line_horizontal miniB_x+150, miniB_y+160, miniB_xsize, 0
	line_horizontal miniB_x+150, miniB_y + miniB_ysize+160 - 1, miniB_xsize, 0
	line_vertical miniB_x+150, miniB_y+160, miniB_ysize, 0
	line_vertical miniB_x+150 + miniB_xsize - 1, miniB_y+160, miniB_ysize, 0
	
	line_horizontal miniB_x+200, miniB_y+160, miniB_xsize, 0
	line_horizontal miniB_x+200, miniB_y + miniB_ysize+160 - 1, miniB_xsize, 0
	line_vertical miniB_x+200, miniB_y+160, miniB_ysize, 0
	line_vertical miniB_x+200 + miniB_xsize - 1, miniB_y+160, miniB_ysize, 0
	
	line_horizontal miniB_x+250, miniB_y+160, miniB_xsize, 0
	line_horizontal miniB_x+250, miniB_y + miniB_ysize+160 - 1, miniB_xsize, 0
	line_vertical miniB_x+250, miniB_y+160, miniB_ysize, 0
	line_vertical miniB_x+250 + miniB_xsize - 1, miniB_y+160, miniB_ysize, 0
	
	line_horizontal miniB_x+300, miniB_y+160, miniB_xsize, 0
	line_horizontal miniB_x+300, miniB_y + miniB_ysize+160 - 1, miniB_xsize, 0
	line_vertical miniB_x+300, miniB_y+160, miniB_ysize, 0
	line_vertical miniB_x+300 + miniB_xsize - 1, miniB_y+160, miniB_ysize, 0
	
	line_horizontal miniB_x+350, miniB_y+160, miniB_xsize, 0
	line_horizontal miniB_x+350, miniB_y + miniB_ysize+160 - 1, miniB_xsize, 0
	line_vertical miniB_x+350, miniB_y+160, miniB_ysize, 0
	line_vertical miniB_x+350 + miniB_xsize - 1, miniB_y+160, miniB_ysize, 0
	
	line_horizontal miniB_x+400, miniB_y+160, miniB_xsize, 0
	line_horizontal miniB_x+400, miniB_y + miniB_ysize+160 - 1, miniB_xsize, 0
	line_vertical miniB_x+400, miniB_y+160, miniB_ysize, 0
	line_vertical miniB_x+400 + miniB_xsize - 1, miniB_y+160, miniB_ysize, 0
	
	line_horizontal miniB_x+450, miniB_y+160, miniB_xsize, 0
	line_horizontal miniB_x+450, miniB_y + miniB_ysize+160 - 1, miniB_xsize, 0
	line_vertical miniB_x+450, miniB_y+160, miniB_ysize, 0
	line_vertical miniB_x+450 + miniB_xsize - 1, miniB_y+160, miniB_ysize, 0
	;////////////////////////////////////////////////////////////////
	;linia6
	;////////////////////////////////////////////////////////////////
	line_horizontal miniB_x, miniB_y+200, miniB_xsize, 0
	line_horizontal miniB_x, miniB_y +200+ miniB_ysize - 1, miniB_xsize, 0
	line_vertical miniB_x, miniB_y+200, miniB_ysize, 0
	line_vertical miniB_x + miniB_xsize - 1, miniB_y+200, miniB_ysize, 0
	
	line_horizontal miniB_x+50, miniB_y+200, miniB_xsize, 0
	line_horizontal miniB_x+50, miniB_y +200+ miniB_ysize - 1, miniB_xsize, 0
	line_vertical miniB_x+50, miniB_y+200, miniB_ysize, 0
	line_vertical miniB_x+50 + miniB_xsize - 1, miniB_y+200, miniB_ysize, 0
	
	line_horizontal miniB_x+100, miniB_y+200, miniB_xsize, 0
	line_horizontal miniB_x+100, miniB_y + miniB_ysize+200 - 1, miniB_xsize, 0
	line_vertical miniB_x+100, miniB_y+200, miniB_ysize, 0
	line_vertical miniB_x+100 + miniB_xsize - 1, miniB_y+200, miniB_ysize, 0
	
	line_horizontal miniB_x+150, miniB_y+200, miniB_xsize, 0
	line_horizontal miniB_x+150, miniB_y + miniB_ysize+200 - 1, miniB_xsize, 0
	line_vertical miniB_x+150, miniB_y+200, miniB_ysize, 0
	line_vertical miniB_x+150 + miniB_xsize - 1, miniB_y+200, miniB_ysize, 0
	
	line_horizontal miniB_x+200, miniB_y+200, miniB_xsize, 0
	line_horizontal miniB_x+200, miniB_y + miniB_ysize+200 - 1, miniB_xsize, 0
	line_vertical miniB_x+200, miniB_y+200, miniB_ysize, 0
	line_vertical miniB_x+200 + miniB_xsize - 1, miniB_y+200, miniB_ysize, 0
	
	line_horizontal miniB_x+250, miniB_y+200, miniB_xsize, 0
	line_horizontal miniB_x+250, miniB_y + miniB_ysize+200 - 1, miniB_xsize, 0
	line_vertical miniB_x+250, miniB_y+200, miniB_ysize, 0
	line_vertical miniB_x+250 + miniB_xsize - 1, miniB_y+200, miniB_ysize, 0
	
	line_horizontal miniB_x+300, miniB_y+200, miniB_xsize, 0
	line_horizontal miniB_x+300, miniB_y + miniB_ysize+200 - 1, miniB_xsize, 0
	line_vertical miniB_x+300, miniB_y+200, miniB_ysize, 0
	line_vertical miniB_x+300 + miniB_xsize - 1, miniB_y+200, miniB_ysize, 0
	
	line_horizontal miniB_x+350, miniB_y+200, miniB_xsize, 0
	line_horizontal miniB_x+350, miniB_y + miniB_ysize+200 - 1, miniB_xsize, 0
	line_vertical miniB_x+350, miniB_y+200, miniB_ysize, 0
	line_vertical miniB_x+350 + miniB_xsize - 1, miniB_y+200, miniB_ysize, 0
	
	line_horizontal miniB_x+400, miniB_y+200, miniB_xsize, 0
	line_horizontal miniB_x+400, miniB_y + miniB_ysize+200 - 1, miniB_xsize, 0
	line_vertical miniB_x+400, miniB_y+200, miniB_ysize, 0
	line_vertical miniB_x+400 + miniB_xsize - 1, miniB_y+200, miniB_ysize, 0
	
	line_horizontal miniB_x+450, miniB_y+200, miniB_xsize, 0
	line_horizontal miniB_x+450, miniB_y + miniB_ysize+200 - 1, miniB_xsize, 0
	line_vertical miniB_x+450, miniB_y+200, miniB_ysize, 0
	line_vertical miniB_x+450 + miniB_xsize - 1, miniB_y+200, miniB_ysize, 0
	;////////////////////////////////////////////////////////////////
	;linia7
	;////////////////////////////////////////////////////////////////
	line_horizontal miniB_x, miniB_y+240, miniB_xsize, 0
	line_horizontal miniB_x, miniB_y +240+ miniB_ysize - 1, miniB_xsize, 0
	line_vertical miniB_x, miniB_y+240, miniB_ysize, 0
	line_vertical miniB_x + miniB_xsize - 1, miniB_y+240, miniB_ysize, 0
	
	line_horizontal miniB_x+50, miniB_y+240, miniB_xsize, 0
	line_horizontal miniB_x+50, miniB_y +240+ miniB_ysize - 1, miniB_xsize, 0
	line_vertical miniB_x+50, miniB_y+240, miniB_ysize, 0
	line_vertical miniB_x+50 + miniB_xsize - 1, miniB_y+240, miniB_ysize, 0
	
	line_horizontal miniB_x+100, miniB_y+240, miniB_xsize, 0
	line_horizontal miniB_x+100, miniB_y + miniB_ysize+240 - 1, miniB_xsize, 0
	line_vertical miniB_x+100, miniB_y+240, miniB_ysize, 0
	line_vertical miniB_x+100 + miniB_xsize - 1, miniB_y+240, miniB_ysize, 0
	
	line_horizontal miniB_x+150, miniB_y+240, miniB_xsize, 0
	line_horizontal miniB_x+150, miniB_y + miniB_ysize+240 - 1, miniB_xsize, 0
	line_vertical miniB_x+150, miniB_y+240, miniB_ysize, 0
	line_vertical miniB_x+150 + miniB_xsize - 1, miniB_y+240, miniB_ysize, 0
	
	line_horizontal miniB_x+200, miniB_y+240, miniB_xsize, 0
	line_horizontal miniB_x+200, miniB_y + miniB_ysize+240 - 1, miniB_xsize, 0
	line_vertical miniB_x+200, miniB_y+240, miniB_ysize, 0
	line_vertical miniB_x+200 + miniB_xsize - 1, miniB_y+240, miniB_ysize, 0
	
	line_horizontal miniB_x+250, miniB_y+240, miniB_xsize, 0
	line_horizontal miniB_x+250, miniB_y + miniB_ysize+240 - 1, miniB_xsize, 0
	line_vertical miniB_x+250, miniB_y+240, miniB_ysize, 0
	line_vertical miniB_x+250 + miniB_xsize - 1, miniB_y+240, miniB_ysize, 0
	
	line_horizontal miniB_x+300, miniB_y+240, miniB_xsize, 0
	line_horizontal miniB_x+300, miniB_y + miniB_ysize+240 - 1, miniB_xsize, 0
	line_vertical miniB_x+300, miniB_y+240, miniB_ysize, 0
	line_vertical miniB_x+300 + miniB_xsize - 1, miniB_y+240, miniB_ysize, 0
	
	line_horizontal miniB_x+350, miniB_y+240, miniB_xsize, 0
	line_horizontal miniB_x+350, miniB_y + miniB_ysize+240 - 1, miniB_xsize, 0
	line_vertical miniB_x+350, miniB_y+240, miniB_ysize, 0
	line_vertical miniB_x+350 + miniB_xsize - 1, miniB_y+240, miniB_ysize, 0
	
	line_horizontal miniB_x+400, miniB_y+240, miniB_xsize, 0
	line_horizontal miniB_x+400, miniB_y + miniB_ysize+240 - 1, miniB_xsize, 0
	line_vertical miniB_x+400, miniB_y+240, miniB_ysize, 0
	line_vertical miniB_x+400 + miniB_xsize - 1, miniB_y+240, miniB_ysize, 0
	
	line_horizontal miniB_x+450, miniB_y+240, miniB_xsize, 0
	line_horizontal miniB_x+450, miniB_y + miniB_ysize+240 - 1, miniB_xsize, 0
	line_vertical miniB_x+450, miniB_y+240, miniB_ysize, 0
	line_vertical miniB_x+450 + miniB_xsize - 1, miniB_y+240, miniB_ysize, 0
	;////////////////////////////////////////////////////////////////
	;linia 8
	;////////////////////////////////////////////////////////////////
	line_horizontal miniB_x, miniB_y+280, miniB_xsize, 0
	line_horizontal miniB_x, miniB_y +280+ miniB_ysize - 1, miniB_xsize, 0
	line_vertical miniB_x, miniB_y+280, miniB_ysize, 0
	line_vertical miniB_x + miniB_xsize - 1, miniB_y+280, miniB_ysize, 0
	
	line_horizontal miniB_x+50, miniB_y+280, miniB_xsize, 0
	line_horizontal miniB_x+50, miniB_y +280+ miniB_ysize - 1, miniB_xsize, 0
	line_vertical miniB_x+50, miniB_y+280, miniB_ysize, 0
	line_vertical miniB_x+50 + miniB_xsize -1 , miniB_y+280, miniB_ysize, 0
	
	line_horizontal miniB_x+100, miniB_y+280, miniB_xsize, 0
	line_horizontal miniB_x+100, miniB_y + miniB_ysize+280 - 1, miniB_xsize, 0
	line_vertical miniB_x+100, miniB_y+280, miniB_ysize, 0
	line_vertical miniB_x+100 + miniB_xsize - 1, miniB_y+280, miniB_ysize, 0
	
	line_horizontal miniB_x+150, miniB_y+280, miniB_xsize, 0
	line_horizontal miniB_x+150, miniB_y + miniB_ysize+280 - 1, miniB_xsize, 0
	line_vertical miniB_x+150, miniB_y+280, miniB_ysize, 0
	line_vertical miniB_x+150 + miniB_xsize - 1, miniB_y+280, miniB_ysize, 0
	
	line_horizontal miniB_x+200, miniB_y+280, miniB_xsize, 0
	line_horizontal miniB_x+200, miniB_y + miniB_ysize+280 - 1, miniB_xsize, 0
	line_vertical miniB_x+200, miniB_y+280, miniB_ysize, 0
	line_vertical miniB_x+200 + miniB_xsize - 1, miniB_y+280, miniB_ysize, 0
	
	line_horizontal miniB_x+250, miniB_y+280, miniB_xsize, 0
	line_horizontal miniB_x+250, miniB_y + miniB_ysize+280 - 1, miniB_xsize, 0
	line_vertical miniB_x+250, miniB_y+280, miniB_ysize, 0
	line_vertical miniB_x+250 + miniB_xsize - 1, miniB_y+280, miniB_ysize, 0
	
	line_horizontal miniB_x+300, miniB_y+280, miniB_xsize, 0
	line_horizontal miniB_x+300, miniB_y + miniB_ysize+280 - 1, miniB_xsize, 0
	line_vertical miniB_x+300, miniB_y+280, miniB_ysize, 0
	line_vertical miniB_x+300 + miniB_xsize - 1, miniB_y+280, miniB_ysize, 0
	
	line_horizontal miniB_x+350, miniB_y+280, miniB_xsize, 0
	line_horizontal miniB_x+350, miniB_y + miniB_ysize+280 - 1, miniB_xsize, 0
	line_vertical miniB_x+350, miniB_y+280, miniB_ysize, 0
	line_vertical miniB_x+350 + miniB_xsize - 1, miniB_y+280, miniB_ysize, 0
	
	line_horizontal miniB_x+400, miniB_y+280, miniB_xsize, 0
	line_horizontal miniB_x+400, miniB_y + miniB_ysize+280 - 1, miniB_xsize, 0
	line_vertical miniB_x+400, miniB_y+280, miniB_ysize, 0
	line_vertical miniB_x+400 + miniB_xsize - 1, miniB_y+280, miniB_ysize, 0
	
	line_horizontal miniB_x+450, miniB_y+280, miniB_xsize, 0
	line_horizontal miniB_x+450, miniB_y + miniB_ysize+280 - 1, miniB_xsize, 0
	line_vertical miniB_x+450, miniB_y+280, miniB_ysize, 0
	line_vertical miniB_x+450 + miniB_xsize - 1, miniB_y+280, miniB_ysize, 0
	;////////////////////////////////////////////////////////////////
	;linia9
	;////////////////////////////////////////////////////////////////
	line_horizontal miniB_x, miniB_y+320, miniB_xsize, 0
	line_horizontal miniB_x, miniB_y +320+ miniB_ysize -1 , miniB_xsize, 0
	line_vertical miniB_x, miniB_y+320, miniB_ysize, 0
	line_vertical miniB_x + miniB_xsize - 1, miniB_y+320, miniB_ysize, 0
	
	line_horizontal miniB_x+50, miniB_y+320, miniB_xsize, 0
	line_horizontal miniB_x+50, miniB_y +320+ miniB_ysize - 1, miniB_xsize, 0
	line_vertical miniB_x+50, miniB_y+320, miniB_ysize, 0
	line_vertical miniB_x+50 + miniB_xsize - 1, miniB_y+320, miniB_ysize, 0
	
	line_horizontal miniB_x+100, miniB_y+320, miniB_xsize, 0
	line_horizontal miniB_x+100, miniB_y + miniB_ysize+320 - 1, miniB_xsize, 0
	line_vertical miniB_x+100, miniB_y+320, miniB_ysize, 0
	line_vertical miniB_x+100 + miniB_xsize - 1, miniB_y+320, miniB_ysize, 0
	
	line_horizontal miniB_x+150, miniB_y+320, miniB_xsize, 0
	line_horizontal miniB_x+150, miniB_y + miniB_ysize+320 - 1, miniB_xsize, 0
	line_vertical miniB_x+150, miniB_y+320, miniB_ysize, 0
	line_vertical miniB_x+150 + miniB_xsize - 1, miniB_y+320, miniB_ysize, 0
	
	line_horizontal miniB_x+200, miniB_y+320, miniB_xsize, 0
	line_horizontal miniB_x+200, miniB_y + miniB_ysize+320 - 1, miniB_xsize, 0
	line_vertical miniB_x+200, miniB_y+320, miniB_ysize, 0
	line_vertical miniB_x+200 + miniB_xsize - 1, miniB_y+320, miniB_ysize, 0
	
	line_horizontal miniB_x+250, miniB_y+320, miniB_xsize, 0
	line_horizontal miniB_x+250, miniB_y + miniB_ysize+320 - 1, miniB_xsize, 0
	line_vertical miniB_x+250, miniB_y+320, miniB_ysize, 0
	line_vertical miniB_x+250 + miniB_xsize - 1, miniB_y+320, miniB_ysize, 0
	
	line_horizontal miniB_x+300, miniB_y+320, miniB_xsize, 0
	line_horizontal miniB_x+300, miniB_y + miniB_ysize+320 - 1, miniB_xsize, 0
	line_vertical miniB_x+300, miniB_y+320, miniB_ysize, 0
	line_vertical miniB_x+300 + miniB_xsize - 1, miniB_y+320, miniB_ysize, 0
	
	line_horizontal miniB_x+350, miniB_y+320, miniB_xsize, 0
	line_horizontal miniB_x+350, miniB_y + miniB_ysize+320 -1, miniB_xsize, 0
	line_vertical miniB_x+350, miniB_y+320, miniB_ysize, 0
	line_vertical miniB_x+350 + miniB_xsize -1, miniB_y+320, miniB_ysize, 0
	
	line_horizontal miniB_x+400, miniB_y+320, miniB_xsize, 0
	line_horizontal miniB_x+400, miniB_y + miniB_ysize+320 - 1, miniB_xsize, 0
	line_vertical miniB_x+400, miniB_y+320, miniB_ysize, 0
	line_vertical miniB_x+400 + miniB_xsize - 1, miniB_y+320, miniB_ysize, 0
	
	line_horizontal miniB_x+450, miniB_y+320, miniB_xsize, 0
	line_horizontal miniB_x+450, miniB_y + miniB_ysize+320 - 1, miniB_xsize, 0
	line_vertical miniB_x+450, miniB_y+320, miniB_ysize, 0
	line_vertical miniB_x+450 + miniB_xsize - 1, miniB_y+320, miniB_ysize, 0
	;////////////////////////////////////////////////////////////////
	;linia 10
	;////////////////////////////////////////////////////////////////
	line_horizontal miniB_x, miniB_y+360, miniB_xsize, 0
	line_horizontal miniB_x, miniB_y +360+ miniB_ysize - 1, miniB_xsize, 0
	line_vertical miniB_x, miniB_y+360, miniB_ysize, 0
	line_vertical miniB_x + miniB_xsize -1, miniB_y+360, miniB_ysize, 0
	
	line_horizontal miniB_x+50, miniB_y+360, miniB_xsize, 0
	line_horizontal miniB_x+50, miniB_y +360+ miniB_ysize - 1, miniB_xsize, 0
	line_vertical miniB_x+50, miniB_y+360, miniB_ysize, 0
	line_vertical miniB_x+50 + miniB_xsize - 1, miniB_y+360, miniB_ysize, 0
	
	line_horizontal miniB_x+100, miniB_y+360, miniB_xsize, 0
	line_horizontal miniB_x+100, miniB_y + miniB_ysize+360 - 1, miniB_xsize, 0
	line_vertical miniB_x+100, miniB_y+360, miniB_ysize, 0
	line_vertical miniB_x+100 + miniB_xsize - 1, miniB_y+360, miniB_ysize, 0
	
	line_horizontal miniB_x+150, miniB_y+360, miniB_xsize, 0
	line_horizontal miniB_x+150, miniB_y + miniB_ysize+360 - 1, miniB_xsize, 0
	line_vertical miniB_x+150, miniB_y+360, miniB_ysize, 0
	line_vertical miniB_x+150 + miniB_xsize - 1, miniB_y+360, miniB_ysize, 0
	
	line_horizontal miniB_x+200, miniB_y+360, miniB_xsize, 0
	line_horizontal miniB_x+200, miniB_y + miniB_ysize+360 - 1, miniB_xsize, 0
	line_vertical miniB_x+200, miniB_y+360, miniB_ysize, 0
	line_vertical miniB_x+200 + miniB_xsize - 1, miniB_y+360, miniB_ysize, 0
	
	line_horizontal miniB_x+250, miniB_y+360, miniB_xsize, 0
	line_horizontal miniB_x+250, miniB_y + miniB_ysize+360 - 1, miniB_xsize, 0
	line_vertical miniB_x+250, miniB_y+360, miniB_ysize, 0
	line_vertical miniB_x+250 + miniB_xsize - 1, miniB_y+360, miniB_ysize, 0
	
	line_horizontal miniB_x+300, miniB_y+360, miniB_xsize, 0
	line_horizontal miniB_x+300, miniB_y + miniB_ysize+360 - 1, miniB_xsize, 0
	line_vertical miniB_x+300, miniB_y+360, miniB_ysize, 0
	line_vertical miniB_x+300 + miniB_xsize - 1, miniB_y+360, miniB_ysize, 0
	
	line_horizontal miniB_x+350, miniB_y+360, miniB_xsize, 0
	line_horizontal miniB_x+350, miniB_y + miniB_ysize+360 - 1, miniB_xsize, 0
	line_vertical miniB_x+350, miniB_y+360, miniB_ysize, 0
	line_vertical miniB_x+350 + miniB_xsize - 1, miniB_y+360, miniB_ysize, 0
	
	line_horizontal miniB_x+400, miniB_y+360, miniB_xsize, 0
	line_horizontal miniB_x+400, miniB_y + miniB_ysize+360 - 1, miniB_xsize, 0
	line_vertical miniB_x+400, miniB_y+360, miniB_ysize, 0
	line_vertical miniB_x+400 + miniB_xsize - 1, miniB_y+360, miniB_ysize, 0
	
	line_horizontal miniB_x+450, miniB_y+360, miniB_xsize, 0
	line_horizontal miniB_x+450, miniB_y + miniB_ysize+360 - 1, miniB_xsize, 0
	line_vertical miniB_x+450, miniB_y+360, miniB_ysize, 0
	line_vertical miniB_x+450 + miniB_xsize - 1, miniB_y+360, miniB_ysize, 0
	
	line_horizontal button_x, button_y+1, button_xsize, 0
	line_vertical button_x+1, button_y, button_ysize, 0
	
	line_vertical 540, 30, 50, 0FF0000h
	line_vertical 541, 30, 50, 0FF0000h
	line_vertical 542, 30, 50, 0FF0000h
	line_vertical 760, 30, 50, 0FF0000h
	line_vertical 761, 30, 50, 0FF0000h
	line_vertical 762, 30, 50, 0FF0000h
	line_vertical 530, 40, 30, 0FF0000h
	line_vertical 531, 40, 30, 0FF0000h
	line_vertical 532, 40, 30, 0FF0000h
	line_vertical 770, 40, 30, 0FF0000h
	line_vertical 771, 40, 30, 0FF0000h
	line_vertical 772, 40, 30, 0FF0000h
	line_vertical 520, 50, 10, 0FF0000h
	line_vertical 521, 50, 10, 0FF0000h
	line_vertical 522, 50, 10, 0FF0000h
	line_vertical 780, 50, 10, 0FF0000h
	line_vertical 781, 50, 10, 0FF0000h
	line_vertical 782, 50, 10, 0FF0000h
	
	;;;;;contur tabela
	;;;;;;tabelul pentru joc
	line_horizontal button_x, button_y-10, button_xsize, 0
	line_horizontal button_x, button_y + button_ysize+10, button_xsize, 0
	line_vertical button_x-10, button_y, button_ysize, 0
	line_vertical button_x + button_xsize+10, button_y, button_ysize, 0
	
	make_text_macro 'A', area, 370, 110
	make_text_macro 'B', area, 370, 150
	make_text_macro 'C', area, 370, 190
	make_text_macro 'D', area, 370, 230
	make_text_macro 'E', area, 370, 270
	make_text_macro 'F', area, 370, 310
	make_text_macro 'G', area, 370, 350
	make_text_macro 'H', area, 370, 390
	make_text_macro 'I', area, 370, 430
	make_text_macro 'J', area, 370, 470
	
	make_text_macro '1', area, 420, 520
	make_text_macro '2', area, 470, 520
	make_text_macro '3', area, 520, 520
	make_text_macro '4', area, 570, 520
	make_text_macro '5', area, 620, 520
	make_text_macro '6', area, 670, 520
	make_text_macro '7', area, 720, 520
	make_text_macro '8', area, 770, 520
	make_text_macro '9', area, 820, 520
	make_text_macro '1', area, 860, 520
	make_text_macro '0', area, 870, 520

final_draw:
	popa
	mov esp, ebp
	pop ebp
	ret
draw endp

start:
	;alocam memorie pentru zona de desenat
	mov eax, area_width
	mov ebx, area_height
	mul ebx  
	shl eax, 2  ;rezultatul il inmultim cu 4 deoarece fiecare pixel din zona desenat o sa ocupe un DW adica 4 bytes
	push eax
	call malloc  
	add esp, 4
	mov area, eax
	;apelam functia de desenare a ferestrei
	; typedef void (*DrawFunc)(int evt, int x, int y);
	; void __cdecl BeginDrawing(const char *title, int width, int height, unsigned int *area, DrawFunc draw);
	
	push offset draw ;procedura principala care se apeleaza de fiecare data cand se intampla un eveniment
	push area  ; zona de desenare si pt care am alocat memorie anterior
	push area_height ;dam dimensiunile
	push area_width
	push offset window_title 
	call BeginDrawing 
	add esp, 20
	
	;terminarea programului
	push 0
	call exit
end start
