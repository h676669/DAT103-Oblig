section .data
    foo db '7368412x509' ; 
    bar db 10          ; Newline character

section .text 
    global _start

_start:
    mov ecx, foo + 2 ; velger foo
    call printdigit ; skiver ut 6

    call printdigit ; skriver ut 6 igjen

    add ecx, 8 ; velger 11 karatker i foo
    call printdigit ; skriver ut 9

    mov ecx,bar
    call printdigit

    mov eax, 1 ; system call for exit 
    mov ebx, 0 ; exiter med stats success 
    int 80h ; slutter programmet 

printdigit:
    mov eax, 4 ; system call for sys_write
    mov ebx, 1 ; stdout 
    mov edx, 1 ; antall bytes som skal skrives
    int 80h
    ret

