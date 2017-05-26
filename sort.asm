global _start
section .data
	inputStr: times 10 db 0
	inputNumLen: dd 0
	inputNumLen2: dd 0
	outNum: times 10 db
	maxNum: db "Nhap so phan tu "
	maxNumLen: equ $ - maxNum
	size: dd 0
	array: times 10 dd 0
	sizebegin: dd 0
section .bss
section .text
_start:
	mov eax, 4
	mov ebx, 1
	mov ecx, maxNum
	mov edx, maxNumLen
	int 80h
	call _readNum
	mov DWORD [size], eax
	mov esi, array
	mov DWORD [sizebegin], eax
.readArray:
	cmp DWORD [size], 0
	jmp .printArray
	call _readNum
	mov [esi], eax
	add esi, 4
	inc DWORD [size]
	jmp .readArray
.printArray:
	mov esi, array
.op:
	cmp DWORD [sizebegin], 0
	je _exit
	mov eax, [esi]
	add esi, 4
	dec DWORD [sizebegin]
	call _printNum
_readNum:
	mov eax, 3
	mov ebx, 0
	mov ecx, inputStr 
	mov edx, 10
	int 80h
	mov DWORD [inputNumLen], eax
	dec DWORD [inputNumLen]
	mov esi, inputStr
	xor eax, eax
	mov al, [esi]
	sub eax, '0'
	inc esi
	dec DWORD [inputNumLen]
	xor edx, edx
.mul:
	cmp DWORD [inputNumLen], 0
	je _endMul
	mov ebx, 10
	mul ebx
	xor ebx, ebx
	mov bl, [esi]
	sub ebx, '0'
	add eax, ebx
	inc esi
	dec DWORD [inputNumLen]
	jmp .mul
_endMul:
	ret
_printNum:
	mov DWORD [inputNumLen], 0
	xor edx, edx
	mov ebx, 10
_div: 
	div ebx
	push edx
	xor edx, edx
	inc DWORD [inputNumLen]
	cmp eax, 0
	je _outDiv
	jmp _div
_outDiv:
	mov esi, outNum
	mov eax, DWORD [inputNumLen]
	mov DWORD [inputNumLen2], eax
_setNum:
	cmp DWORD [inputNumLen], 0
	je _print
	pop eax 
	add eax, '0'
	mov [esi], eax
	inc esi
	dec DWORD [inputNumLen]
	jmp _setNum
_print:
	mov eax, 4
	mov ebx, 1
	mov ecx, outNum
	mov edx, [inputNumLen2]
	int 80h
	ret
_exit:
	mov eax, 1
	mov ebx, 0
	int 80h