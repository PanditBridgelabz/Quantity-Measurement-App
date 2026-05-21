import { measurementOptions } from '../utils/constants'

function QuantityForm({
  quantity,
  setQuantity,
  label,
  selectedType,
}) {

  const units = measurementOptions[selectedType]

  return (
    <div className="quantity-box">
      <h3>{label}</h3>

      <input
        type="number"
        placeholder="Enter value"
        value={quantity.value}
        onChange={(e) =>
          setQuantity({ ...quantity, value: e.target.value })
        }
      />

      <select
        value={quantity.unit}
        onChange={(e) =>
          setQuantity({ ...quantity, unit: e.target.value })
        }
      >
        {units.map((unit) => (
          <option key={unit} value={unit}>
            {unit}
          </option>
        ))}
      </select>
    </div>
  )
}

export default QuantityForm