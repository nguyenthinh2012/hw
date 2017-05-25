global _start
section .data
	input: DB "Input: "
	inputLen equ $ - input
	find: db "Find: "
	findLen equ $ - find
	iStr: times 100 db '0'
	fStr: times 100 db '0'
	count: dq 0
	indexInInput: dq 1
	indexInFind: dq 1
	lenFindStr: dq 0
	lenInput: dq 0
	check: dq 0
	index: dq 0
	space: dq " "
	enter: db 10
	strSum: db "tong so vi tri tim thay: "
	sumLen equ $ -strSum
	strVitri: db "tai cac vi tri: "
	strVitriLen equ $ - strVitri
	array: times 100 dq 0
section .bss
section .text
_start:
	; read INPUT string and sub String
	mov rax, 1
	mov rdi, 1
	mov rsi, input
	mov edx, inputLen
	syscall
	mov rax, 0
	mov rdi, 0
	mov rsi, iStr
	mov rdx, 100
	syscall
	mov [lenInput], rax
	mov rax, 1
	mov rdi, 1
	mov rsi, find
	mov rdx, findLen
	syscall
	mov rax, 0
	mov rdi, 0
	mov rsi, fStr
	mov rdx, 100
	syscall
	mov [lenFindStr], rax
	mov rsi, iStr
	mov rdi, fStr
.find:
	mov rax, [lenInput]
	cmp QWORD [indexInInput], rax
	je .checkEnd
	mov al, [rsi]
	mov bl, [rdi]
	cmp al, bl
	je .bothInc
	cmp qword [check], 1
	je .reNewFind
	inc qword [indexInInput]
	inc rsi
	jmp .find
.checkEnd:
	mov rax, [check]
	cmp rax,1
	je .checkLen
	jmp .print
.checkLen:
	mov rax, [lenFindStr]
	cmp rax, [indexInFind]
	je .push
	jmp .print
.reNewFind:
	mov rax, [lenFindStr]
	cmp qword [indexInFind], rax
	je .push
	mov rax, [indexInFind]
	cmp rax, 2
	ja .loopx
.temp:
	mov rdi, fStr
	mov qword [indexInFind], 1
	mov qword [check],0
	jmp .find
.loopx:
	cmp qword [lenFindStr], 2
	je .temp
	dec rsi
	dec DWORD [indexInInput]
	sub rax, 1
	cmp rax, 2
	ja .loopx
	jmp .temp
.bothInc:
	inc rsi
	inc rdi
	add QWORD [indexInInput], 1
	add QWORD [indexInFind],1
	mov QWORD [check],1		
	jmp .find
.push:
	mov rax, [indexInInput]
	sub QWORD rax, [lenFindStr]
	push rax
	inc QWORD [count]
	mov rax, [lenFindStr]
	jmp .loopx
.print:
	mov rax, 1
	mov rdi, 1
	mov rsi, strSum
	mov rdx, sumLen
	syscall
	mov rax, [count]
	add rax, '0'
	mov [check], rax
	mov rax, 1
	mov rdi, 1
	mov rsi, check
	mov rdx, 1
	syscall
	mov eax, 1 
	mov rdi, 1
	mov rsi, enter
	mov rdx, 1
	syscall
	mov rax, 1 
	mov rdi, 1
	mov rsi, strVitri
	mov rdx, strVitriLen
	syscall
	mov rsi, array
	mov QWORD rax, [count]
	mov QWORD [check], rax
	add QWORD [check], rax

.print2:
	cmp qword [count], 0
	je .back
	sub qword [count],1
	pop rax
	
	mov [rsi], rax
	add rsi, 8
	mov rax, [space]
	mov [rsi], rax
	add rsi, 8
	jmp .print2
	mov rsi, array
.back:
	cmp QWORD [check], 0
	je .exit
	sub rsi, 8
	mov rax, [esi]
	add rax, '0'
	mov QWORD [index], rax
	mov rax, 1
	mov rdi, 1
	mov rsi, index
	mov rdx, 1
	syscall
	dec QWORD [check]
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

