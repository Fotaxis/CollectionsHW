package ru.naumen.collection.task1;

/**
 * Билет
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class Ticket {
    private long id;
    private String client;

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        Ticket objectTicket = (Ticket) obj;
        return objectTicket.id == id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}
