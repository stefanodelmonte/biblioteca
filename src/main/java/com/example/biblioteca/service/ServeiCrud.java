package com.example.biblioteca.service;

import java.util.List;
import java.util.Optional;

/**
 * Interfície genèrica que defineix les operacions CRUD bàsiques
 * que tots els serveis de l'aplicació han d'implementar.
 *
 * Ús del polimorfisme i interfícies (RA7): qualsevol servei que implementi
 * aquesta interfície pot ser tractat de manera uniforme on calgui.
 *
 * @param <T> El tipus d'entitat que gestiona el servei (Llibre, Lectura, etc.)
 */
public interface ServeiCrud<T> {

    /** Retorna totes les entitats de la taula corresponent. */
    List<T> llistarTots();

    /** Busca una entitat per la seva clau primària. Retorna Optional buit si no existeix. */
    Optional<T> buscarPerId(Long id);

    /** Desa una entitat nova (INSERT) o actualitza una d'existent (UPDATE). */
    T guardar(T entitat);

    /** Elimina l'entitat amb l'id indicat. */
    void eliminar(Long id);
}
