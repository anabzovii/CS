def cifru_cezar_doua_chei(text, cheie1, cheie2, operatie):
    alfabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    text = text.upper()  # Transformăm textul în majuscule
    rezultat = ""

    for caracter in text:
        if caracter in alfabet:
            if operatie == "criptare":
                pozitie1 = (alfabet.index(caracter) + cheie1) % 26
                pozitie2 = (alfabet.index(caracter) + cheie2) % 26
            elif operatie == "decriptare":
                pozitie1 = (alfabet.index(caracter) - cheie1) % 26
                pozitie2 = (alfabet.index(caracter) - cheie2) % 26
            caracter_nou = alfabet[pozitie1] + alfabet[pozitie2]
            rezultat += caracter_nou
        else:
            rezultat += caracter  # Păstrăm spațiile și alte caractere nemodificate

    return rezultat

def main():
    operatie = input("Introduceți operația (criptare/decriptare): ").lower()
    cheie1 = int(input("Introduceți prima cheie (1-25): "))
    cheie2 = input("Introduceți a doua cheie (doar litere, lungime >= 7): ")

    if cheie1 < 1 or cheie1 > 25:
        print("Prima cheie trebuie să fie între 1 și 25 inclusiv.")
        return
    if not cheie2.isalpha() or len(cheie2) < 7:
        print("A doua cheie trebuie să conțină doar litere și să aibă lungimea de cel puțin 7 caractere.")
        return

    text = input("Introduceți mesajul sau criptograma: ")

    if operatie == "criptare" or operatie == "decriptare":
        rezultat = cifru_cezar_doua_chei(text, cheie1, len(cheie2), operatie)
        print(f"Rezultatul este: {rezultat}")
    else:
        print("Operație invalidă. Folosiți 'criptare' sau 'decriptare'.")

if __name__ == "__main__":
    main()