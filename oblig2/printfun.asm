section .data
foo db '7368412x669 '  ; Original data containing digits
bar db 10              ; Line feed character for newline

section .text
global _start

_start:
    ; Print first digit ('6')
    mov ecx, foo
    add ecx, 8          ; Move to the position of the first '6'
    call printdigit

    ; Print second digit ('6')
    mov ecx, foo
    add ecx, 9          ; Move to the position of the second '6'
    call printdigit

    ; Print third digit ('9')
    mov ecx, foo
    add ecx, 10         ; Move to the position of '9'
    call printdigit

    ; Print newline character
    mov edx, 1
    mov ecx, bar
    mov ebx, 1
    mov eax, 4
    int 80h

    ; Exit program
    mov ebx, 0
    mov eax, 1
    int 80h

printdigit:
    ; Print the digit pointed by ecx
    mov edx, 1           ; Number of bytes to write
    mov ebx, 1           ; File descriptor (stdout)
    mov eax, 4           ; Syscall number (sys_write)
    int 80h              ; Make syscall
    ret
