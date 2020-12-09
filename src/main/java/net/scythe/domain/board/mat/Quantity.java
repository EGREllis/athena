package net.scythe.domain.board.mat;

import net.scythe.domain.resource.Resource;

public class Quantity {
    private final Resource resource;
    private final int quantity;

    public Quantity(Resource resource, int quantity) {
        this.resource = resource;
        this.quantity = quantity;
    }

    public Resource getResource() {
        return resource;
    }

    public int getQuantity() {
        return quantity;
    }
}
