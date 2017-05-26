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
	space: dd " "
	enter: db 10
	strSum: db "tong so vi tri tim thay: "
	sumLen equ $ -strSum
	strVitri: db "tai cac vi tri: "
	strVitriLen equ $ - strVitri
	array: times 100 dd 0
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
	mov eax, [indexInFind]
	cmp eax, 2
	ja .loopx
.temp:
	mov edi, fStr
	mov DWORD [indexInFind], 1
	mov DWORD [check],0
	jmp .find
.loopx:
	cmp DWORD [lenFindStr], 2
	je .temp
	dec esi
	dec DWORD [indexInInput]
	sub eax, 1
	cmp eax, 2
	ja .loopx
	jmp .temp
.bothInc:
	inc esi
	inc edi
	add DWORD [indexInInput], 1
	add DWORD [indexInFind],1
	mov DWORD [check],1		
	jmp .find
.push:
	mov eax, [indexInInput]
	sub DWORD eax, [lenFindStr]
	push eax
	inc DWORD [count]
	mov eax, [lenFindStr]
	jmp .loopx
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
	mov esi, array
	mov DWORD eax, [count]
	mov DWORD [check], eax
	add dword[check], eax

.print2:
	cmp DWORD [count], 0
	je .back
	sub DWORD [count],1
	pop eax
	add eax, '0'
	mov [esi], eax
	add esi, 4
	mov eax, [space]
	mov [esi], eax
	add esi, 4
	jmp .print2
	mov esi, array
.back:
	cmp DWORD [check], 0
	je .exit
	sub esi, 4
	mov eax, [esi]
	mov DWORD [index], eax
	mov eax, 4
	mov ebx, 1
	mov ecx, index
	mov edx, 1
	int 80h
	dec DWORD [check]
	jmp .back
.exit:
	mov eax, 4
	mov ebx, 1
	mov ecx, enter
	mov edx, 1
	int 80h
    mov eax, 1          ; Exit 
    mov ebx, 0          
    int 80h             

