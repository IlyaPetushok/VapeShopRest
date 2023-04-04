package project.vapeshop.predicate;

import jakarta.persistence.metamodel.SingularAttribute;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
//import javax.persistence.metamodel.SingularAttribute;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CustomPredicate<T> {
    private T value;
    private T maxValue;

    private Class<T> tClass;

    private List<T> values;

    private SingularAttribute<?,T> nameField;

    private ComparisonType compType;

    public CustomPredicate(List<T> values, SingularAttribute<?, T> nameField, ComparisonType compType,Class<T> tClass) {
        this.values = values;
        this.nameField = nameField;
        this.compType = compType;
        this.tClass= tClass;
    }

    public CustomPredicate(T value, SingularAttribute<?, T> nameField, ComparisonType compType) {
        this.value = value;
        this.nameField = nameField;
        this.compType = compType;
    }

    public CustomPredicate(T value, T maxValue, SingularAttribute<?, T> nameField, ComparisonType compType,Class<T> tClass) {
        this.value = value;
        this.maxValue = maxValue;
        this.tClass = tClass;
        this.nameField = nameField;
        this.compType = compType;
    }
}
