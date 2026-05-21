import axios from "axios"

const API_BASE = "http://13.235.113.253/api/v1/quantities"

function OperationCard({
  operation,
  payload,
  setResult,
  token,
  hideTargetUnit = false,
}) {

  const callApi = async () => {
    try {
      const response = await axios.post(
        `${API_BASE}/${operation}`,
        payload,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      )

      setResult(JSON.stringify(response.data, null, 2))
    } catch (error) {
      console.error(error)

      if (error.response?.data) {
        setResult(JSON.stringify(error.response.data, null, 2))
      } else {
        setResult("Something went wrong")
      }
    }
  }

  return (
    <button
      onClick={callApi}
      style={{
        padding: "10px 20px",
        margin: "10px",
        border: "none",
        borderRadius: "8px",
        cursor: "pointer",
        background: "#2563eb",
        color: "white",
        fontWeight: "bold",
      }}
    >
      {operation.toUpperCase()}
    </button>
  )
}

export default OperationCard
