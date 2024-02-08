package service;

import entity.Map;
import entity.Ship;

public interface MapService {
    Map generateMap(Ship[] ships);
    void printMap(Map[] map);


}
