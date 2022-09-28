package com.zss.takeout.dto;

import com.zss.takeout.entity.Setmeal;
import com.zss.takeout.entity.SetmealDish;
import com.zss.takeout.entity.Setmeal;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
