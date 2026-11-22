import {useState} from "react";

export function useForm(valoresIniciais){

    const [dados, setDados] = useState(valoresIniciais);
    const [enviando, setEnviando] = useState(false);

    function definirCampo(campo, valor){

        setDados((anterior) => ({...anterior, [campo]: valor}));

    }

    async function enviar(funcaoApi, {aoSucesso, aoErro} = {}){

        setEnviando(true);

        try {
            const resposta = await funcaoApi(dados);
            if (aoSucesso){
                aoSucesso(resposta);
            }
        }catch (erro){
            console.error(erro);
            if(aoErro){
                aoErro(erro);
            }
        }finally {
            setEnviando(false);
        }

    }

    function limpar(){

        setDados(valoresIniciais);

    }

    return{dados, definirCampo, enviar, enviando, limpar};

}