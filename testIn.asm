global _start

section .data
	strO : db "so thu "
	strLen equ $ - strO
	num: dd 123
	count: dd 0
	str: times 10 db 0
	str1: times 10 db 0
	count2: dd 0
	tes : dd 0
	array: times 10 dd 0
	beginNum: dd 1
section .bss
section .text

_start:
	
	mov eax , 3
	mov ebx, 0
	mov ecx, str
	mov edx, 10
	int 80h
	mov DWORD [count], eax
	dec DWORD[count]
	mov esi , str
	xor eax,eax
	mov al, [esi]
	sub eax, '0'
	inc esi
	dec DWORD [count]	
	xor edx, edx
_mul:
	cmp DWORD [count], 0
	je _endMul
	mov ebx, 10 
	mul ebx
	xor ebx,ebx
	mov bl, [esi]
	sub ebx, '0'
	add eax, ebx
	inc esi
	dec DWORD [count]
	jmp _mul
_endMul:
	mov DWORD [num], eax
_startDiv:
	xor edx, edx
	mov ebx, 10
	jmp _div
_div:
	div ebx
	push edx
	xor edx, edx
	inc DWORD [count]
	cmp eax, 0
	je _outDiv
	jmp _div
_outDiv:
	mov esi, str1
	mov eax, DWORD [count]
	mov DWORD [count2], eax
_setNum:
	cmp DWORD [count], 0
	je _print
	pop eax
	add eax, '0'
	mov [esi], eax
	inc esi
	dec DWORD [count]
	jmp _setNum
_print:
	mov eax, 4
	mov ebx, 1
	mov ecx, str1
	mov edx, [count2]
	int 80h
.exit:
	mov eax, 1          ; Exit 
	mov ebx, 0          
	int 80h             
