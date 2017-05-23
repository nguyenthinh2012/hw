global _start
section .data
	input: DB "Input: "
	inputLen equ $ - input
	find: db "Find: "
	findLen equ $ - find
	iStr: times 100 db '0'
	fStr: times 100 db '0'
	count: dd 0
	indexInInput: dd 1
	indexInFind: dd 1
	lenFindStr: dd 0
	lenInput: dd 0
	check: dd 0
	index: dd 0
	space: db " "
	enter: db 10
	strSum: db "tong so vi tri tim thay: "
	sumLen equ $ -strSum
	strVitri: db "tai cac vi tri: "
	strVitriLen equ $ - strVitri
section .bss
section .text
_start:
	; read INPUT string and sub String
	mov eax, 4
	mov ebx, 1
	mov ecx, input
	mov edx, inputLen
	int 80h
	mov eax, 3
	mov ebx, 0
	mov ecx, iStr
	mov edx, 100
	int 80h
	mov [lenInput], eax
	mov eax, 4
	mov ebx, 1
	mov ecx, find
	mov edx, findLen
	int 80h
	mov eax, 3
	mov ebx, 0
	mov ecx, fStr
	mov edx, 100
	int 80h
	mov [lenFindStr], eax
	mov esi, iStr
	mov edi, fStr
.find:
	mov eax, [lenInput]
	cmp DWORD [indexInInput], eax
	je .checkEnd
	mov al, [esi]
	mov bl, [edi]
	cmp al, bl
	je .bothInc
	cmp DWORD [check], 1
	je .reNewFind
	inc DWORD [indexInInput]
	inc esi
	jmp .find
.checkEnd:
	mov eax, [check]
	cmp eax,1
	je .checkLen
	jmp .print
.checkLen:
	mov eax, [lenFindStr]
	cmp eax, [indexInFind]
	je .push
	jmp .print
.reNewFind:
	mov eax, [lenFindStr]
	cmp DWORD [indexInFind], eax
	je .push
	mov edi, fStr
	mov DWORD [indexInFind], 1
	mov DWORD [check],0
	jmp .find
.bothInc:
	inc esi
	inc edi
	add DWORD [indexInInput], 1
	add DWORD [indexInFind],1
	mov DWORD [check],1		
	jmp .find
.push:
	mov eax, [indexInInput]
	mov DWORD [index], eax
	mov eax, [index]
	sub DWORD eax, [lenFindStr]
	push eax
	mov edi, fStr
	mov DWORD [indexInFind] ,1 
	mov DWORD [check],0
	inc DWORD [count]	
        mov ecx, [lenFindStr]
        sub ecx, 1
        .loopback:
            inc esi
        loop .loopback
	jmp .find
.print:
	mov eax, 4
	mov ebx, 1
	mov ecx, strSum
	mov edx, sumLen
	int 80h
	mov eax, [count]
	add eax, '0'
	mov [check], eax
	mov eax, 4 
	mov ebx, 1
	mov ecx, check
	mov edx, 1
	int 80h
	mov eax, 4 
	mov ebx, 1
	mov ecx, enter
	mov edx, 1
	int 80h
	mov eax, 4 
	mov ebx, 1
	mov ecx, strVitri
	mov edx, strVitriLen
	int 80h
.print2:
	cmp DWORD [count], 0
	je .exit
	sub DWORD [count],1
	pop eax
	add eax, '0'
	mov [index], eax
	mov eax, 4
	mov ebx,1
	mov ecx, index
	mov edx,1
	int 80h
	mov eax, 4
	mov ebx, 1
	mov ecx, space
	mov edx,1
	int 80h
	jmp .print2
.exit:
	mov eax, 4
	mov ebx, 1
	mov ecx, enter
	mov edx, 1
	int 80h
    mov eax, 1          ; Exit 
    mov ebx, 0          
    int 80h             

