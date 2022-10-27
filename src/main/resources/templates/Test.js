function GenerateForm() {
      const [food, setFood] = React.useState({
        name: "",
        instructions: "",
        ingredients: [{
          ingredient: "",
          amount: "",
          unit: ""
        }]
      });

      const [inputFields, setInputFields] = React.useState([
        {
          ingredient: "",
          amount: "",
          unit: ""
        }

      ]);

      const addFields = () => {
        const newField = {
          ingredient: "",
          amount: "",
          unit: ""
        }
        setInputFields([...inputFields, newField])
      }

      const handleChange = (e) => {
        setFood({ ...food, [e.target.name]: e.target.value });
      }

      const handleFormChange = (index, event) => {
        const data = [...inputFields];
        data[index][event.target.name] = event.target.value;
        setFood({ ...food, ingredients: data });
      }

      const submit = (e) => {
        e.preventDefault();
        console.log(food);
      }

      const remove = (index) => {
        const data = [...inputFields];
        data.slice(index, 1);
        setFood({ ...food, ingredients: data });
      }


      React.useEffect(() => {
        fetch("http://localhost:8080/units")
          .then(response => response.json())
          .then(data => console.log(data))
      }, [])


      return (
        <div>
          <form onSubmit={submit}>
            <input
              type="text"
              name="name"
              placeholder="Nimi"
              value={food.name}
              onChange={handleChange}
            />
            <input
              type="text"
              name="instructions"
              placeholder="Ohjeet"
              value={food.instructions}
              onChange={handleChange}
            />
            {inputFields.map((input, index) => {
              return (
                <div key={index}>
                  <input
                    type="text"
                    name="ingredient"
                    placeholder="Ainesosa"
                    value={food.ingredient}
                    
                  />
                  <input
                    type="text"
                    name="amount"
                    placeholder="Määrä"
                    value={food.amount}
                    
                  />
                  <input
                    type="text"
                    name="unit"
                    placeholder="Yksikkö"
                    value={food.unit}
                    
                  />
                  
                </div>

              )
            })}
            <input type="submit" value="Lähetä" />
          </form > <button onClick={addFields}>+</button>

        </div >);

    }
    
    const root = ReactDOM.createRoot(document.getElementById("root"));
    root.render(<Test />);